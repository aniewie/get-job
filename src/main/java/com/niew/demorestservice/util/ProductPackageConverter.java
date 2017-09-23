package com.niew.demorestservice.util;

import com.niew.demorestservice.domain.Product;
import com.niew.demorestservice.domain.ProductPackage;
import com.niew.demorestservice.dto.ProductData;
import com.niew.demorestservice.dto.ProductPackageDataIn;
import com.niew.demorestservice.dto.ProductPackageDataOut;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProductPackageConverter {
    private static final BigDecimal HUNDRED = new BigDecimal("100");
    public static ProductPackageDataOut convertToDTO(ProductPackage productPackage, BigDecimal currencyExchangeRate, String currency) {
        ProductPackageDataOut dto = new ProductPackageDataOut();
        dto.setId(productPackage.getId());
        dto.setName(productPackage.getName());
        dto.setDescription(productPackage.getDescription());
        dto.setPriceInCurrency(currencyExchangeRate.multiply(BigDecimal.valueOf(productPackage.getPrice())).divide(HUNDRED, 2, BigDecimal.ROUND_HALF_UP));
        dto.setCurrency(currency);
        dto.getProducts().addAll(productPackage.getProducts().stream().map(ProductPackageConverter::convertProductToDTO).collect(Collectors.toList()));
        return dto;
    }
    public static ProductPackageDataOut convertToDTO(ProductPackage productPackage) {
        return ProductPackageConverter.convertToDTO(productPackage, null, null);
    }

    public static ProductPackage convertFromDto(ProductPackageDataIn dto) {
        List<Product> products = dto.getProducts().stream().map(ProductPackageConverter::convertProductFromDTO).collect(Collectors.toList());
        ProductPackage entity = new ProductPackage();
        entity.update(dto.getName(), dto.getDescription(), products);
        return entity;
    }
    private static ProductData convertProductToDTO(Product product) {
        ProductData dto = new ProductData();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setPriceInUsdCents(product.getUsdPrice());
        dto.setCount(product.getCount());
        return dto;
    }
    public static Product convertProductFromDTO(ProductData dto) {
        Product entity = new Product(dto.getCount(), dto.getProductId(), dto.getName(), dto.getPriceInUsdCents());
        return entity;
    }
}
