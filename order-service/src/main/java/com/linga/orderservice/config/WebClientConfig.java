package com.linga.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

//This class is a config class used to call the other services using HTTP (Synchronous)
@Configuration
public class WebClientConfig {

//    A new bean is created with the name of the method
//    Client side Load Balancing means when there is a request , services discovery has confusing that which instance to call when multiple instances of same service is running
//    TO resolve this we use the following bean
    @Bean
    @LoadBalanced // this will add client side load balancer
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

//    This bean is used when there is no Client side Load Balancing is setup
//    @Bean
//    public WebClient webClient(){
//        return  WebClient.builder().build();
//    }
}
