package com.niew.demorestservice.service;

import com.niew.demorestservice.dto.ProductData;
import com.niew.demorestservice.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
/**
 * Retrieval of valid product types
 */
@Service
public class ProductServiceImpl implements ProductService {


    private final RestTemplate restTemplate;

    public ProductServiceImpl(RestTemplateBuilder restTemplateBuilder, @Value("${application.productEndpoint.userName}") String userName, @Value("${application.productEndpoint.password}") String password) {
        this.restTemplate = restTemplateBuilder.basicAuthorization(userName, password).build();
    }
    /**
     * Retrieves current data of product (understood as valid product type) from https://product-service.herokuapp.com/api/v1/products
     * Caches the output (no cache eviction configured, so each product will be retrieved once and stored)
     * @param id - id of a product (type)
     * @return - product data
     * @Throws  ProductNotFoundException if product with given id has not been found in a service call
     */
    @Override
    @Cacheable("products")
    public ProductData getProductById(String id) {
        try {
            ProductData product = this.restTemplate.getForObject("https://product-service.herokuapp.com/api/v1/products/{id}", ProductData.class, id);
            product.setProductId(id);
            return product;
        } catch (HttpClientErrorException ex) {
            if (ex.getRawStatusCode() == HttpStatus.NOT_FOUND.value()) {
                throw new ProductNotFoundException("No product with id: " + id);
            }
            throw ex;
        }
    }

}
