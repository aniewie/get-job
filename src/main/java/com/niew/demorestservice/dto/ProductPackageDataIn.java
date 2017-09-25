package com.niew.demorestservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for input parameters of /package API. Allows duplicated products (more than one item of the same productId)
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ProductPackageDataIn {

    public ProductPackageDataIn() {}
    public ProductPackageDataIn(String name, String description, List<ProductData> products) {
        this.name = name;
        this.description = description;
        this.products = products;
    }

    @NotNull
    private String name;

    private String description;

    @NotEmpty
    @Valid
    private List<ProductData> products = new ArrayList<>();

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

    public List<ProductData> getProducts() {
        return products;
    }

    public void setProducts(List<ProductData> products) {
        this.products = products;
    }
}


