package com.niew.demorestservice;

import com.niew.demorestservice.dto.ProductPackageData;
import com.niew.demorestservice.exception.PackageNotFoundException;
import com.niew.demorestservice.service.ProductPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ProductPackageData deletePackage(@PathVariable("id") Long id) {
        return service.deletePackage(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    ProductPackageData createPackage(@RequestBody @Valid ProductPackageData data) {
        return service.createPackage(data);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ProductPackageData update(@PathVariable("id") Long id, @RequestBody @Valid ProductPackageData data) {
        return service.updatePackage(id, data);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handlePackageNotFound(PackageNotFoundException ex) {
    }
}
