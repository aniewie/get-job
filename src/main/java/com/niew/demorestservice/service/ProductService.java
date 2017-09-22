package com.niew.demorestservice.service;

import com.niew.demorestservice.dto.ProductData;

public interface ProductService {
    ProductData getProductById(String id);
}
