package com.niew.demorestservice.util;

import com.niew.demorestservice.domain.Product;
import com.niew.demorestservice.domain.ProductPackage;
import com.niew.demorestservice.dto.ProductPackageData;
import com.niew.demorestservice.dto.ProductData;

import java.util.List;
import java.util.stream.Collectors;

public class ProductPackageConverter {

    public static ProductPackageData convertToDTO(ProductPackage productPackage) {
        ProductPackageData dto = new ProductPackageData();
        dto.setPackageId(productPackage.getId());
        dto.setName(productPackage.getName());
        dto.setDescription(productPackage.getDescription());
        dto.setPriceInCurrency(productPackage.getPrice());
        dto.getProducts().addAll(productPackage.getProducts().stream().map(ProductPackageConverter::convertProductToDTO).collect(Collectors.toList()));
        return dto;
    }
    public static ProductPackage convertFromDto(ProductPackageData dto) {
        List<Product> products = dto.getProducts().stream().map(ProductPackageConverter::convertProductFromDTO).collect(Collectors.toList());
        ProductPackage entity = new ProductPackage(dto.getName(), dto.getDescription(), products);
        return entity;
    }
    private static ProductData convertProductToDTO(Product product) {
        ProductData dto = new ProductData();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setUsdPrice(product.getUsdPrice());
        dto.setCount(product.getCount());
        return dto;
    }
    private static Product convertProductFromDTO(ProductData dto) {
        Product entity = new Product(dto.getCount(), dto.getProductId(), dto.getName(), dto.getUsdPrice());
        return entity;
    }
}
