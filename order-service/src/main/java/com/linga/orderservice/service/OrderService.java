package com.linga.orderservice.service;

import com.linga.orderservice.dto.InventoryResponse;
import com.linga.orderservice.dto.OrderLineItemsDto;
import com.linga.orderservice.dto.OrderRequest;
import com.linga.orderservice.event.OrderPlacedEvent;
import com.linga.orderservice.model.Order;
import com.linga.orderservice.model.OrderLineItems;
import com.linga.orderservice.respository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private  OrderRepository orderRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    Logger logger = LoggerFactory.getLogger("skuCodesLogger");
    public  String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsList()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();
        //call to the inventory service(Synchronous) to check where the product is in stock
        InventoryResponse[] inventoryResponses = webClientBuilder.build().get().uri("http://inventory-service/api/inventory", uriBuilder
                        -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();// block will make synchronous request

        assert inventoryResponses != null;
        boolean isAllInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);
        if(isAllInStock){
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
            return "Order Places Successfully";
        }else{
            throw new IllegalArgumentException("Product is not in stock , We will notify when it is available");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }


}
