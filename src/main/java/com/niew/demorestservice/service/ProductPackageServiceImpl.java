package com.niew.demorestservice.service;

import com.niew.demorestservice.ProductPackageRepository;
import com.niew.demorestservice.domain.ProductPackage;
import com.niew.demorestservice.dto.ProductData;
import com.niew.demorestservice.dto.ProductPackageData;
import com.niew.demorestservice.exception.PackageNotFoundException;
import com.niew.demorestservice.service.ProductPackageService;
import com.niew.demorestservice.util.ProductPackageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductPackageServiceImpl implements ProductPackageService {

    @Autowired
    private ProductPackageRepository repository;
    @Autowired
    private ProductService productService;

    @Override
    public List<ProductPackageData> listAllPackages() {
        List<ProductPackage> list = repository.findAll();
        return list.stream().map(ProductPackageConverter::convertToDTO).collect(toList());
    }
    @Override
    public ProductPackageData retrievePackage(Long id, String currency) {
        ProductPackage entity = repository.findOne(id).orElseThrow(() -> new PackageNotFoundException(id.toString()));
        //TODO waluty
        return ProductPackageConverter.convertToDTO(entity);
    }
    @Override
    public ProductPackageData deletePackage(Long id) {
        ProductPackage entity = repository.findOne(id).orElseThrow(() -> new PackageNotFoundException(id.toString()));
        repository.delete(entity);
        return ProductPackageConverter.convertToDTO(entity);
    }
    @Override
    public ProductPackageData createPackage(ProductPackageData dto) {
        for (ProductData product: dto.getProducts()) {
            ProductData externalProduct = this.productService.getProductById(product.getProductId());
            product.setName(externalProduct.getName());
            product.setUsdPrice(externalProduct.getUsdPrice());
        }
        ProductPackage entity = repository.save(ProductPackageConverter.convertFromDto(dto));
        return ProductPackageConverter.convertToDTO(entity);
    }
}
