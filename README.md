# E-microservices-parent
This is a microservices project for E-commerce application developed using Spring Boot and Spring Cloud

# Services Used:-

1. Product-service :- Create the products 
  Technolgies:- Spring Boot, MongoDB
2. Order-Service :- Order the products
  Technolgies:- Spring Boot, MySQL
3. Inventory-service :- Check if the product is available
  Technolgies:- Spring Boot, MySQL
  
# Interprocess Communication between Services:-

The communication between Order Process and Inventory Process is done through Web Client 
This is a synchronous Commuinication , the order service will wait for the response form inventory service while placing an order for the product

# Service Discovery Using Netflix Eureka

Since we Hard Coded the the request to the inventory service , In reqal world the instances are dynamic when deployed to cloud and the IP addresses will be changed
Based on the load new instances may be created or destroyed. So we need to have a list of services and their Ip address to be stored in a registry such as using a pattern of Discovery service 



