package com.niew.demorestservice.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for output parameters of /package API
 */
public class ProductPackageDataOut {

    private Long id;

    private String name;

    private String description;

    private BigDecimal priceInCurrency;

    private String currency;

    private List<ProductData> products = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPriceInCurrency() {
        return priceInCurrency;
    }

    public void setPriceInCurrency(BigDecimal priceInCurrency) {
        this.priceInCurrency = priceInCurrency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<ProductData> getProducts() {
        return products;
    }

    public void setProducts(List<ProductData> products) {
        this.products = products;
    }
}
