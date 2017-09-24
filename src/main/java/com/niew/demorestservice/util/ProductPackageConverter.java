package com.niew.demorestservice.util;

import com.niew.demorestservice.domain.Product;
import com.niew.demorestservice.domain.ProductPackage;
import com.niew.demorestservice.dto.ProductData;
import com.niew.demorestservice.dto.ProductPackageDataIn;
import com.niew.demorestservice.dto.ProductPackageDataOut;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

    private static ProductData convertProductToDTO(Product product) {
        ProductData dto = new ProductData();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setUsdPrice(product.getUsdPrice());
        dto.setCount(product.getCount());
        return dto;
    }
    public static List<Product> convertProductsFromDtoGrouping(List<ProductData> products) {
        Map<String, Integer> productsCountById = products.stream().collect(Collectors.groupingBy(ProductData::getProductId,
                Collectors.summingInt(ProductData::getCount)));
        Map<String, ProductData> productsById = products.stream().collect(Collectors.toMap(ProductData::getProductId, Function.identity(), (p1, p2) -> p1, LinkedHashMap::new));
        List<Product> result = productsById.values().stream().map(p -> ProductPackageConverter.convertProductFromDTO(p, productsCountById.get(p.getProductId()))).collect(Collectors.toList());
        return result;
    }
    public static Product convertProductFromDTO(ProductData dto, int count) {
        Product entity = new Product(count, dto.getProductId(), dto.getName(), dto.getUsdPrice());
        return entity;
    }
}
