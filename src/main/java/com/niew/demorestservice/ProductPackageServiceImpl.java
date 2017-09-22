package com.niew.demorestservice;

import com.niew.demorestservice.domain.ProductPackage;
import com.niew.demorestservice.dto.ProductPackageData;
import com.niew.demorestservice.util.ProductPackageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductPackageServiceImpl implements ProductPackageService {

    @Autowired
    private ProductPackageRepository repository;

    @Override
    public List<ProductPackageData> listAllPackages() {
        List<ProductPackage> list = repository.findAll();
        return list.stream().map(ProductPackageConverter::convertToDTO).collect(toList());
    }
    @Override
    public ProductPackageData retrievePackage(Long id, String currency) {
        ProductPackage entity = this.repository.findProductPackageById(id);
        //TODO waluty
        return ProductPackageConverter.convertToDTO(entity);
    }
}
