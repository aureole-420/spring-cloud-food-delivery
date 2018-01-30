# spring-cloud-food-delivery

Bug: Services can be registered on Eureka, but cannot be called using service name directly, i.e., one still need to hardcode (at `application.properties` level) `localhost:portnumber` in order to use the service.

## Target: Develop a food delivery application using Microservices architecture and Spring Cloud
------------
### Functional Requirements:
User can search a restaurant based on restaurant name. Then user can order food by choosing different menu item, quantity and add a note about his/her diet restrictions and etc. User can also fills in the delivery address. After user places an order, the order should contain food items user ordered, quantity, price and order time. User then needs to pay for his/her order by providing credit card number, expiration date, and security code. After payment is made successfully, it should return payment ID, timestamp and then the order is considered as completed so the user can see the estimated delivery time.
### Non-Functional Requirements:
- Use Microservices architecture so that each backend service can be developed and deployed individually.
- It’s up to you to choose either SQL or No-SQL database and how many databases you need
- The design should be object-oriented and uses best practices I talked in the class.
- REST API should be designed based on the principles I talked in the class
- There should be reasonable amount of unit tests, integration tests and mvc tests.
- There should be at least 1 place to handle payment error case
- Security is not a concern here.

## Summary on structuring:
-----------
The spring-clould-food-delivery project is made of four micro-service, i.e.

|                             Service Name | Description                                                      | Features                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
|-----------------------------------------:|------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|                             menu-display | A standalone microservice                                        | 1. REST API Support Uploading/Getting menus through HTTP requests. 2. Use Mongo DB for storage                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
|                    order-payment-service | A cloud native microservice handling (A) orders and (B) payments | 1.1  REST API Supports submitting *order* through HTTP.POST.  1.2  Front end can be notified through web socket (so that the user can be directed to payment webpage) after successful order placement --- 因为没写前端，所以这个功能没有具体实现。 2.1 REST API supports submitting *payment* through HTTP.POST2.2 Backend supports elementary credit card validation2.3.1 Failed payment can be notified to the Frontend through web socket --- 因为没写前端，所以这个功能没有具体实现。 2.2 Valid payment (including order information) will be published on MQ by calling order-distribution-service.  |
|               order-distribution-service | A cloud native micro service supporting message distribution     | REST API supports publishing message on rabbit mq. (Feeder)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
|              delivery-estimation-service | A cloud native microservice dealing with delivery estimation     | 1. Act as message consumer. 2. Process the message and calculate the estimate delivery time, which is then notified to the user by web socket (not)                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| platform/eureka                          | Service Registration and discovery                               |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| platform/food-delivery-hystrix-dashboard | Service Circuit Breaker                                          |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |


## Testing
------------
### To launch the application:  

* For mac/win user, start `docker-quickstart terminal`
  * For mac users, run `docker-machine restart default` then `eval $(docker-machine env default)`
* cd to `/running-information-service`. Run `docker-compose up` to start the docker container for MongoDB and RabbitMQ
* Note: You might need to modify `spring.data.mongodb.uri` in `application.properties` for some microservices for successful connection with mongodb.

### testing `menu-display` service (standalone)

* sample testing data: `spring-cloud-food-delivery/menu-display/src/main/resources/menuInfo.json`
  - post menu, http.post `http://localhost:9010/upload` with Json data as request body.
  - get menu, http.get `http://localhost:9010/menu/getAll`
  
### testing `order-payment-service`,`order-distribution-service`,`delivery-estimation-service` services (Cloud native)
* start `eureka-server`, `food-delivery-hystrix-dashboard`,`order-distribution-service`,`delivery-estimation-service`, `order-payment-service` sequentially.
* Browse `http://localhost:8761/` for Eureka info page.
* Browse `http://localhost:15672/` for RabbitMQ info page.
* Browse `http://localhost:7979/hystix` for Hystrix info page.

* Should find that all three services are registered on Eureka

* sample testing data for *ordering*: `spring-cloud-food-delivery/order-payment-service/src/main/resources/OrderInfo.json`
  - post order request: http.post `http://localhost:9011/orderInfo` with Json data as request body
  - get order info: http.get `http://localhost:9011/orderInfo/retrieve`
  
* sample testing data for *payment*: `spring-cloud-food-delivery/order-payment-service/src/main/resources/PaymentInfo.json`
  - post payment request: http.post `http://localhost:9011/paymentInfo` with Json data as request body
  - Use original `PaymentInfo.json` for http.post will trigger message distribution. 1. Messages goes into RabbitMQ 2. Distribution service log `"Receiving validated paymentInfo from order-payment service: " + validatedPaymentInfo` 3. Delivery-estimation-service log `"<Consumer> Estimated delivery time: " + estimatedDeliveryTime` 
  - Modify `PaymentInfo.json` (say increase CVC to 4 digits) for http.post will trigger erro log `<Rest.processPayment> credit card service failed.` 
  
* Shutting down any of the three services will not lead to service crash.

  




