package com.niew.demorestservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductData {

    @Min(1)
    private int count;

    @NotNull
    private String productId;

    private String name;

    private Long priceInUsdCents;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPriceInUsdCents() {
        return priceInUsdCents;
    }

    public void setPriceInUsdCents(Long priceInUsdCents) {
        this.priceInUsdCents = priceInUsdCents;
    }
}
