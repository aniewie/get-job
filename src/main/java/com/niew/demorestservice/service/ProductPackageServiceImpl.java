package com.niew.demorestservice.service;

import com.niew.demorestservice.ProductPackageRepository;
import com.niew.demorestservice.domain.Product;
import com.niew.demorestservice.domain.ProductPackage;
import com.niew.demorestservice.dto.ProductData;
import com.niew.demorestservice.dto.ProductPackageDataIn;
import com.niew.demorestservice.dto.ProductPackageDataOut;
import com.niew.demorestservice.exception.PackageNotFoundException;
import com.niew.demorestservice.util.ProductPackageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductPackageServiceImpl implements ProductPackageService {

    @Autowired
    private ProductPackageRepository repository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ExchangeRateService exchangeRateService;
    @Value("${application.defaultCurrency}")
    private String defaultCurrency;

    @Override
    public List<ProductPackageDataOut> listAllPackages() {
        Collection<ProductPackage> list = new LinkedHashSet<>(repository.findAll());
        return list.stream().map(p -> ProductPackageConverter.convertToDTO(p, BigDecimal.ONE, this.defaultCurrency)).collect(toList());
    }
    @Override
    public ProductPackageDataOut retrievePackage(Long id, String currency) {
        ProductPackage entity = this.findPackageById(id);
        BigDecimal exchangeRate = this.exchangeRateService.getExchangeRate(this.defaultCurrency, currency);
        return ProductPackageConverter.convertToDTO(entity, exchangeRate, currency);
    }
    @Override
    public ProductPackageDataOut deletePackage(Long id) {
        ProductPackage entity = this.findPackageById(id);
        repository.delete(entity);
        return ProductPackageConverter.convertToDTO(entity, BigDecimal.ONE, this.defaultCurrency);
    }
    @Override
    public ProductPackageDataOut createPackage(ProductPackageDataIn dto) {
        List<Product> products = ProductPackageConverter.convertProductsFromDtoGrouping(dto.getProducts());
        products.stream().forEach(this::updateProductDataWithValuesFromExtSvc);
        ProductPackage entity = new ProductPackage();
        entity.update(dto.getName(), dto.getDescription(), products);
        entity = repository.save(entity);
        return ProductPackageConverter.convertToDTO(entity, BigDecimal.ONE, this.defaultCurrency);
    }
    @Override
    public ProductPackageDataOut updatePackage(Long id, ProductPackageDataIn dto) {
        List<Product> products = ProductPackageConverter.convertProductsFromDtoGrouping(dto.getProducts());
        products.stream().forEach(this::updateProductDataWithValuesFromExtSvc);
        ProductPackage entity = this.findPackageById(id);
        entity.update(dto.getName(), dto.getDescription(), products);
        entity = repository.save(entity);
        return ProductPackageConverter.convertToDTO(entity, BigDecimal.ONE, this.defaultCurrency);
    }
    void updateProductDataWithValuesFromExtSvc(Product product) {
        ProductData externalProduct = this.productService.getProductById(product.getProductId());
        product.update(product.getCount(), externalProduct.getName(),externalProduct.getUsdPrice());
    }
    ProductPackage findPackageById(Long packageId) {
        return repository.findOne(packageId).orElseThrow(() -> new PackageNotFoundException("No package with id: " + packageId.toString()));
    }
}
