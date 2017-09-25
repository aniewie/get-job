package com.niew.demorestservice.service;

import com.niew.demorestservice.ProductPackageController;
import com.niew.demorestservice.dto.ProductPackageDataIn;
import com.niew.demorestservice.dto.ProductPackageDataOut;

import java.util.List;

/**
 * Conversion and calling DB methods for packages
 */
public interface ProductPackageService {

    /**
     * Retrieves all packages
     * @see ProductPackageController#listAllPackages()
     */
    List<ProductPackageDataOut> listAllPackages();
    /**
     * Retrieves one package with price in given currency
     * @see ProductPackageController#retrievePackage(Long, String)
     */
    ProductPackageDataOut retrievePackage(Long id, String currency);
    /**
     * Deletes package with given id
     * @see ProductPackageController#deletePackage(Long)
     */
    ProductPackageDataOut deletePackage(Long id);
    /**
     * Creates package with the data
     * @see ProductPackageController#createPackage(ProductPackageDataIn)
     */
    ProductPackageDataOut createPackage(ProductPackageDataIn dto);
    /**
     * Updates data in package with given id according to the data in parameter
     * @see ProductPackageController#updatePackage(Long, ProductPackageDataIn)
     */
    ProductPackageDataOut updatePackage(Long id, ProductPackageDataIn dto);
}
