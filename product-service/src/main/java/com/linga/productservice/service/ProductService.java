package com.linga.productservice.service;

import com.linga.productservice.controller.ProductController;
import com.linga.productservice.dto.ProductRequest;
import com.linga.productservice.dto.ProductResponse;
import com.linga.productservice.model.Product;
import com.linga.productservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    private  Logger logger = LoggerFactory.getLogger(ProductController.class);
    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        var prod =  productRepository.save(product);
        logger.info("Product {} is saved", product.getId());
        return this.mapToProductResponse(prod);
    }
    public List<ProductResponse> getAllProduct() {
        List<Product> products = productRepository.findAll();
        logger.info("The products are fetched successfully");
        return products.stream().map(this::mapToProductResponse).toList();
    }

    //this is a helper function used to return the product Response instead of product
    //using @Builder annotation in the model(Product, Product Request, Product Response)
    // we can now create a instance as below using build method.
    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
