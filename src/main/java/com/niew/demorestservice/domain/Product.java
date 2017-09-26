package com.niew.demorestservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Domain model for product in a package (denormalized, each holds product's info: name and usdPrice);
 * actual products (understood as product types) are not stored in DB
 * Different packages may contain the same products (meaning product types --> productId)
 * There may be more items of the same type in the package, but it is reflected by the attribute count of the product in package
 * More instances of product with the same productId are not allowed
 */
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * Number of items of given product type in a package
     */
    private int count;

    private String productId;

    private String name;
    /**
     * Null price - I don'w know if it has any sense (can come like this from external source)
     * I will leave it like these and treat as 0 by calculating package size
     */
    private Long usdPrice;

    public Product() {}
    public Product(Product product) {
        this(product.count, product.productId, product.name, product.usdPrice);
        this.id = product.id;
    }
    public Product(int count, String productId, String name, Long usdPrice) {
        this.productId = productId;
        this.update(count, name, usdPrice);
    }
    public void update(int count, String name, Long usdPrice) {
        this.count = count;
        this.name = name;
        this.usdPrice = usdPrice;
    }
    public int getCount() {
        return count;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public Long getUsdPrice() {
        return usdPrice;
    }

}
