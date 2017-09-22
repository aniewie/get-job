package com.niew.demorestservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class ProductPackage {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    public ProductPackage(){};

    public ProductPackage(String name) {
        this.name = name;
    }
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
