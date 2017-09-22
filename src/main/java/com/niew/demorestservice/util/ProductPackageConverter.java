package com.niew.demorestservice.util;

import com.niew.demorestservice.domain.ProductPackage;
import com.niew.demorestservice.dto.ProductPackageData;

public class ProductPackageConverter {

    public static ProductPackageData convertToDTO(ProductPackage productPackage) {
        ProductPackageData dto = new ProductPackageData(productPackage.getId(), productPackage.getName());
        return dto;
    }
}
