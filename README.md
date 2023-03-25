# E-microservices-parent
This is a microservices project for E-commerce application developed using Spring Boot and Spring Cloud

# Services Used:-

1. Product-service :- Create the products,
   Technologies:- Spring Boot, MongoDB
2. Order-Service :- Order the products,
  Technologies:- Spring Boot, MySQL
3. Inventory-service :- Check if the product is available,
   Technologies:- Spring Boot, MySQL
4. Discovery Service :- Used for service discovery,
Technologies :- Netflix Eureka of spring Cloud
5. Api Gateway Service :- Used as api gateway for routing, load balancing
   , Technologies:- Spring Cloud Gateway.
6. Circuit Breaker:- Used for resilience 
   Technologies:- Spring cloud Resilience4j
7. Event Driven Architecture :- used Kafka for sending a notification(Async),
   Technologies:- Apache Kafka Spring cloud
  
# Interprocess Communication between Services:-

The communication between Order Process and Inventory Process is done through Web Client 
This is a synchronous Communication , the order service will wait for the response form inventory service while placing an order for the product

# Service Discovery Using Spring Cloud Netflix Eureka

Since we Hard Coded the request to the inventory service , In real world the instances are dynamic when deployed to cloud and the IP addresses will be changed
Based on the load new instances may be created or destroyed. So we need to have a list of services and their Ip address to be stored in a registry such as using a pattern of Discovery service 

# API Gateway using Spring Cloud Gateway

Gateways are used to route, provide authorization, SSL etc. Here we define routes to all our services using api gateway running at a specific port.
Through this  i.e http://localhost:8080/api/service-name all the services are accessed and also load balanced.


# Circuit Breaker using Spring Cloud Resilience4j

Used to maintain resilience to the services with states and helps in maintaining fallback logics, Timeouts , retry

# Event Driven Architecture using Kafka

Kafka is used to create microservices in a event driven pattern . Here when an order is placed the notification sent event is triggered and the consumer who is consuming the even listens and proceeds with the message which it receives
Here OrderService is the producer and the Notification Service is the consumer