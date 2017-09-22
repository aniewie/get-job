package com.niew.demorestservice.dto;

import java.util.ArrayList;
import java.util.List;

public class ProductPackageData {

    private Long id;

    private String name;

    private String description;

    private Long priceInCurrency;

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

    public Long getPriceInCurrency() {
        return priceInCurrency;
    }

    public void setPriceInCurrency(Long priceInCurrency) {
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
