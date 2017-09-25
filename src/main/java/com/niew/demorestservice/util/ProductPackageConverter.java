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

/**
 * Utility class converting DTO to entity objects and the othet way round
 */
public class ProductPackageConverter {
    private static final BigDecimal HUNDRED = new BigDecimal("100");

    /**
     * Converts package entity to DTO recalculating its price in USD cents to given currency by given currencyExchangeRate
     * @param productPackage - domain entity of a package
     * @param currencyExchangeRate - exchange rate from USD to currency
     * @param currency - currency
     * @return - DTO of a package that will be returned via API
     */
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

    /**
     * Converts single product in a package from JPA entity to DTO
     * @param product - JPA entity of a product in package
     * @return - DTO of a product
     */
    private static ProductData convertProductToDTO(Product product) {
        ProductData dto = new ProductData();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setUsdPrice(product.getUsdPrice());
        dto.setCount(product.getCount());
        return dto;
    }

    /**
     * Converts list of dto with products to a list of JPA entities accumulating products of the same type to one item
     * The resulting item will have the number of items (count) being a sum of counts of all products of the same kind (productId)
     * @param products - list of DTOs with products, duplications allowed
     * @return - list of JPA entities, no duplications (duplications summed)
     */
    public static List<Product> convertProductsFromDtoGrouping(List<ProductData> products) {
        Map<String, Integer> productsCountById = products.stream().collect(Collectors.groupingBy(ProductData::getProductId,
                Collectors.summingInt(ProductData::getCount)));
        Map<String, ProductData> productsById = products.stream().collect(Collectors.toMap(ProductData::getProductId, Function.identity(), (p1, p2) -> p1, LinkedHashMap::new));
        List<Product> result = productsById.values().stream().map(p -> ProductPackageConverter.convertProductFromDTO(p, productsCountById.get(p.getProductId()))).collect(Collectors.toList());
        return result;
    }

    /**
     * Converts single product from dto to JPA entity, setting resulting count to count from parameter
     * @param dto
     * @param count
     * @return
     */
    public static Product convertProductFromDTO(ProductData dto, int count) {
        Product entity = new Product(count, dto.getProductId(), dto.getName(), dto.getUsdPrice());
        return entity;
    }
}
