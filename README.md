# E-microservices-parent
This is a microservices project for E-commerce application developed using Spring Boot and Spring Cloud

# Services Used:-

1. Product-service :- Create the products,
   Technologies:- Spring Boot, MongoDB
2. Order-Service :- Order the products,
  Technologies:- Spring Boot, MySQL
3. Inventory-service :- Check if the product is available,
   Technologies:- Spring Boot, MySQL
4. Discovery Service :- Netflix Eureka of spring Cloud
  
# Interprocess Communication between Services:-

The communication between Order Process and Inventory Process is done through Web Client 
This is a synchronous Communication , the order service will wait for the response form inventory service while placing an order for the product

# Service Discovery Using Spring Cloud Netflix Eureka

Since we Hard Coded the request to the inventory service , In real world the instances are dynamic when deployed to cloud and the IP addresses will be changed
Based on the load new instances may be created or destroyed. So we need to have a list of services and their Ip address to be stored in a registry such as using a pattern of Discovery service 

# API Gateway using Spring Cloud Gateway

Gateways are used to route, provide authorization, SSL etc. Here we define routes to all our services using api gateway running at a specific port.
Through this  i.e http://localhost:8080/api/service-name all the services are accessed and also load balanced.




