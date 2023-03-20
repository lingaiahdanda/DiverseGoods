package com.linga.productservice.controller;

import com.linga.productservice.dto.ProductRequest;
import com.linga.productservice.dto.ProductResponse;
import com.linga.productservice.model.Product;
import com.linga.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {


    @Autowired
    private ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    //Why are we not returning product itself instead of productRequest and Product Response
    // We are using dto to separate the model entities and dtos
    // since you don't want to expose the models to outside world instead you expose the DTO
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProduct();
    }
}
