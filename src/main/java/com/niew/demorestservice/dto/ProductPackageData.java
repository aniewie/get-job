package com.niew.demorestservice.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ProductPackageData {

    private Long packageId;

    @NotNull
    private String name;

    private String description;

    private Long priceInCurrency;

    private String currency;

    @NotEmpty
    @Valid
    private List<ProductData> products = new ArrayList<>();

    public Long getPackageId() {
        return packageId;
    }

    public void setPackageId(Long packageId) {
        this.packageId = packageId;
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
