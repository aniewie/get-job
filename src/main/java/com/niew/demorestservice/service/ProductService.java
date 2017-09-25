package com.niew.demorestservice.service;

import com.niew.demorestservice.dto.ProductData;

/**
 * Retrieval of valid product types
 */
public interface ProductService {
    /**
     * Retrieves current data of product (understood as valid product type)
     * @param id - id of a product (type)
     * @return - product data
     */
    ProductData getProductById(String id);
}
