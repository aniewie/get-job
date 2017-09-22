package com.niew.demorestservice;

import com.niew.demorestservice.dto.ProductPackageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/package")
public class ProductPackageController {

    @Autowired
    private ProductPackageService service;

    @RequestMapping(method = RequestMethod.GET)
    List<ProductPackageData> listAllPackages() {
        return service.listAllPackages();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ProductPackageData retrievePackage(@PathVariable("id") Long id, @RequestParam(value="currency", defaultValue="USD") String currency) {
        return service.retrievePackage(id, currency);
    }
}
