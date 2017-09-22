package com.niew.demorestservice.service;

import com.niew.demorestservice.dto.ProductPackageData;

import java.util.List;

public interface ProductPackageService {

    List<ProductPackageData> listAllPackages();

    ProductPackageData retrievePackage(Long id, String currency);

    ProductPackageData deletePackage(Long id);

    ProductPackageData createPackage(ProductPackageData dto);
}
