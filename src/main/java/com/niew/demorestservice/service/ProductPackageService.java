package com.niew.demorestservice.service;

import com.niew.demorestservice.dto.ProductPackageDataIn;
import com.niew.demorestservice.dto.ProductPackageDataOut;

import java.util.List;

public interface ProductPackageService {

    List<ProductPackageDataOut> listAllPackages();

    ProductPackageDataOut retrievePackage(Long id, String currency);

    ProductPackageDataOut deletePackage(Long id);

    ProductPackageDataOut createPackage(ProductPackageDataIn dto);

    ProductPackageDataOut updatePackage(Long id, ProductPackageDataIn dto);
}
