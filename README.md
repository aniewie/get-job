# Demo restful service for managing packages of products.

This is a Maven project and Spring Boot app. Download source code and run `./mvnw spring-boot:run` from the main folder.
You can also build a jar with `./mvnw package` and run it on Java.

* Retrieve all packages GET http://localhost:8080/package
* Create new package POST http://localhost:8080/package
* Delete a package DELETE http://localhost:8080/package/{id}
* Retrieve one package showing price in chosen currency GET http://localhost:8080/package/{id}?currency={curr}
* Update package PUT http://localhost:8080/package/{id}

You can list and test all available methods using Swagger UI: http://localhost:8080/swagger-ui.html#!/product-package-controller
It will show the model of input parameters for the POST and PUT requests.
The application will have some test data inserted (you should see some packages).

# System assumptions

* The list of products from Heroku serves as repository of valid product types.
* Each package can contain any number of products (> 0) and different packages can contain the same products.
* When creating/updating package, only `name` is mandatory. Valid package must contain at least one product.
* When creating/updating package, products in package are identified by `productId` (serving as product type). This field is mandatory.
* There is a property called `count` for a product, meaning a number of items of a given type in package. It must be > 0.
* There can be more than one item of the same type in one package. This is achieved by `count` parameter of a product or by placing multiple products of the same type on the list, while creating/updating package. Duplicated entries will be accumulated during save to one entry (by summing `count`s).
* Creation/update ignore product `name` and `price` from input, consulting product repository for these values and effectively saving package with the values from product service.
* Update of package allows adding new products, deleting products and updating product count. Update of package updates price and name of all products in the package, even those unchanged by the client, using values from product service.
