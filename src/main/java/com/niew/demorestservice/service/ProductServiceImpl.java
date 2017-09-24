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

@Service
public class ProductServiceImpl implements ProductService {


    private final RestTemplate restTemplate;

    public ProductServiceImpl(RestTemplateBuilder restTemplateBuilder, @Value("${application.productEndpoint.userName}") String userName, @Value("${application.productEndpoint.password}") String password) {
        this.restTemplate = restTemplateBuilder.basicAuthorization(userName, password).build();
    }

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
