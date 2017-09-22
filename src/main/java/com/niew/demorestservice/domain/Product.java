package com.niew.demorestservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long productInPackageId;

    private int count;

    private String productId;

    private String name;

    private Long usdPrice;

    public Product() {}

    public Product(int count, String productId, String name, Long usdPrice) {
        this.count = count;
        this.productId = productId;
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
