package com.linga.inventoryservice.service;

import com.linga.inventoryservice.dto.InventoryResponse;
import com.linga.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @SneakyThrows // this is used when we don't want to explicitly write a throws exception in method signature
    // Never use in Production s
    public List<InventoryResponse> isInStock(List<String> skuCode){
//        log.info("Wait Started");
        //This is used to slow down the response from inventory to order to see if the circui t breaker is functioning as expected
//        Thread.sleep(10000);
//        log.info("Wait Ended");
        return inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .inStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }
}
