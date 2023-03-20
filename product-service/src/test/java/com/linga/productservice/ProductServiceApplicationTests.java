package com.linga.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linga.productservice.dto.ProductRequest;
import com.linga.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers  //To let junit5 know we use test containers for the test
@AutoConfigureMockMvc // Even we auto-wire we need to autoconfigure the mock mvc
class ProductServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;
	@Container
	// to let junit know this is a docker container, and static to access
	//since the mongoDbContainer is depricated,  so we need to specify the docker image
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	@DynamicPropertySource
	// this annotation will add this property url to the spring context dynamically when running tests
	// This method is called when the tests are runnning and set the url with mongodb test db url
	static  void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		// this is way to get the mongoDB container url
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String convertedProductString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(post("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(convertedProductString))
		.andExpect(status().isCreated());

		//checking whether the product is in DB
		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	@Test
	void shouldGetAllProducts() throws Exception {
		mockMvc.perform(get("/api/product")).andExpect(status().isOk());
		Assertions.assertEquals(1,productRepository.findAll().size());
	}
	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Iphone 13 pro")
				.description("iphone 12")
				.price(BigDecimal.valueOf(1000))
				.build();

	}

}
