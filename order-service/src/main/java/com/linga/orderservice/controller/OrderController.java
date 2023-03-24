package com.linga.orderservice.controller;

import com.linga.orderservice.dto.OrderRequest;
import com.linga.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //This is used when we need circuit breaker anf fallback is when inventory service go down the method executes
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    //TimeLimiter is used when the call to inventory service is made and that service taking more time to response
    //due to performance issue
    // the name should be same as the name of instance you have given in application.properties file as below
    // resilience4j.circuitbreaker.instances.[[[inventory]]] Here .registerHealthIndicator=true
    //completableFuture is used to run the following method in a separate thread asynchronously and can know the status of this thread when Timelimiter is used
//    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody  OrderRequest orderRequest){
        return  CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));
    }

    public CompletableFuture<String>  fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()->"Oops! Something went wrong, Please Order your favourite product after Sometime");
    }
}
