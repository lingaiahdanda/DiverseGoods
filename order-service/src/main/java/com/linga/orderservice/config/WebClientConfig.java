package com.linga.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

//This class is a config class used to call the other services using HTTP (Synchronous)
@Configuration
public class WebClientConfig {

//    A new bean is created with the name of the method
    @Bean
    public WebClient webClient(){
        return  WebClient.builder().build();
    }
}
