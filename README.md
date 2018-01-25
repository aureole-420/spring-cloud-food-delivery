# spring-cloud-food-delivery

Bug: Services can be registered on Eureka, but cannot be called using service name directly, i.e., one still need to hardcode (at `application.properties` level) `localhost:portnumber` in order to use the service.

## Target: Develop a food delivery application using Microservices architecture and Spring Cloud

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

## Summary on structure:
