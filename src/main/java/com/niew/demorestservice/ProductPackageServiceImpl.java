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
        List<ProductPackage> result = repository.findAll();
        return result.stream().map(ProductPackageConverter::convertToDTO).collect(toList());
    }
}
