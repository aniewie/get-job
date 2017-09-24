package com.niew.demorestservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private int count;

    private String productId;

    private String name;

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
