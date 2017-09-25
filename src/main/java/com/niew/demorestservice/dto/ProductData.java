package com.niew.demorestservice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * DTO for input and output from /package API and for retrieving types of products from product-service.herokuapp.com
 */
public class ProductData {

    @Min(1)
    private int count;

    @NotNull
    private String productId;

    private String name;

    private Long usdPrice;

    public ProductData() {}

    public ProductData(int count, String productId, String name, Long usdPrice) {
        this.count = count;
        this.productId = productId;
        this.name = name;
        this.usdPrice = usdPrice;
    }

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
