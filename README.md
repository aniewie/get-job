Demo restful service for managing packages of products

This is a Maven project and Spring Boot app. Download source code and run ./mvnw spring-boot:run from the main folder.

* Retrieve all packages GET http://localhost:8080/package
* Create new package POST http://localhost:8080/package
* Delete a package DELETE http://localhost:8080/package/{id}
* Retrieve one package showing price in chosen currency GET  http://localhost:8080/package/{id}?currency={curr}
* Update package PUT http://localhost:8080/package/{id}

You can list and test all available methods using swagger UI http://localhost:8080/swagger-ui.html#!/product-package-controller.
It will show model of input parameters for POST and PUT requests.
The application will some test data inserted (you should see some packages).