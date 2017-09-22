package com.niew.demorestservice.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ProductPackage {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private Long price;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "productPackageId")
    private List<Product> products = new ArrayList<>();

    public ProductPackage(){};

    public ProductPackage(String name, String description, List<Product> products) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.products = products;
        this.price = products.stream().mapToLong(product -> product.getCount() * product.getUsdPrice()).sum();
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Long getPrice() {
        return price;
    }

    public List<Product> getProducts() {
        return products;
    }
}
