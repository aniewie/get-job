package com.niew.demorestservice.dto;

import javax.validation.constraints.NotNull;

public class ProductData {

    private int count;

    @NotNull
    private String productId;

    private String name;

    private Long usdPrice;

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

    public Long getUsdPrice() {
        return usdPrice;
    }

    public void setUsdPrice(Long usdPrice) {
        this.usdPrice = usdPrice;
    }
}
