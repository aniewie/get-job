package com.niew.demorestservice.service;

import com.niew.demorestservice.ProductPackageController;
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

    /**
     * Retrieves all packages
     * @see ProductPackageController#listAllPackages()
     */
    @Override
    public List<ProductPackageDataOut> listAllPackages() {
        Collection<ProductPackage> list = new LinkedHashSet<>(repository.findAll());
        return list.stream().map(p -> ProductPackageConverter.convertToDTO(p, BigDecimal.ONE, this.defaultCurrency)).collect(toList());
    }
    /**
     * Retrieves one package with price in given currency
     * @see ProductPackageController#retrievePackage(Long, String)
     */
    @Override
    public ProductPackageDataOut retrievePackage(Long id, String currency) {
        ProductPackage entity = this.findPackageById(id);
        BigDecimal exchangeRate = this.exchangeRateService.getExchangeRate(this.defaultCurrency, currency);
        return ProductPackageConverter.convertToDTO(entity, exchangeRate, currency);
    }
    /**
     * Deletes package with given id
     * @see ProductPackageController#deletePackage(Long)
     */
    @Override
    public ProductPackageDataOut deletePackage(Long id) {
        ProductPackage entity = this.findPackageById(id);
        repository.delete(entity);
        return ProductPackageConverter.convertToDTO(entity, BigDecimal.ONE, this.defaultCurrency);
    }
    /**
     * Creates package with the data
     * @see ProductPackageController#createPackage(ProductPackageDataIn)
     */
    @Override
    public ProductPackageDataOut createPackage(ProductPackageDataIn dto) {
        List<Product> products = ProductPackageConverter.convertProductsFromDtoGrouping(dto.getProducts());
        products.stream().forEach(this::updateProductDataWithValuesFromExtSvc);
        ProductPackage entity = new ProductPackage();
        entity.update(dto.getName(), dto.getDescription(), products);
        entity = repository.save(entity);
        return ProductPackageConverter.convertToDTO(entity, BigDecimal.ONE, this.defaultCurrency);
    }
    /**
     * Updates data in package with given id according to the data in parameter
     * @see ProductPackageController#updatePackage(Long, ProductPackageDataIn)
     */
    @Override
    public ProductPackageDataOut updatePackage(Long id, ProductPackageDataIn dto) {
        List<Product> products = ProductPackageConverter.convertProductsFromDtoGrouping(dto.getProducts());
        products.stream().forEach(this::updateProductDataWithValuesFromExtSvc);
        ProductPackage entity = this.findPackageById(id);
        entity.update(dto.getName(), dto.getDescription(), products);
        entity = repository.save(entity);
        return ProductPackageConverter.convertToDTO(entity, BigDecimal.ONE, this.defaultCurrency);
    }
    /*
        Update product fields with current data from productService (external API)
    */
    void updateProductDataWithValuesFromExtSvc(Product product) {
        ProductData externalProduct = this.productService.getProductById(product.getProductId());
        product.update(product.getCount(), externalProduct.getName(),externalProduct.getUsdPrice());
    }
    ProductPackage findPackageById(Long packageId) {
        return repository.findOne(packageId).orElseThrow(() -> new PackageNotFoundException("No package with id: " + packageId.toString()));
    }
}
