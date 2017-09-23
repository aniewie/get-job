package com.niew.demorestservice;

import com.niew.demorestservice.dto.ProductPackageDataIn;
import com.niew.demorestservice.dto.ProductPackageDataOut;
import com.niew.demorestservice.exception.PackageNotFoundException;
import com.niew.demorestservice.service.ProductPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/package")
public class ProductPackageController {

    private static final Logger logger = LoggerFactory.getLogger(ProductPackageController.class);

    @Autowired
    private ProductPackageService service;

    @RequestMapping(method = RequestMethod.GET)
    List<ProductPackageDataOut> listAllPackages() {
        return service.listAllPackages();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ProductPackageDataOut retrievePackage(@PathVariable("id") Long id, @RequestParam(value="currency", defaultValue="USD") String currency) {
        return service.retrievePackage(id, currency);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ProductPackageDataOut deletePackage(@PathVariable("id") Long id) {
        return service.deletePackage(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    ProductPackageDataOut createPackage(@RequestBody @Valid ProductPackageDataIn data) {
        return service.createPackage(data);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ProductPackageDataOut update(@PathVariable("id") Long id, @RequestBody @Valid ProductPackageDataIn data) {
        return service.updatePackage(id, data);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No package found")
    public void handlePackageNotFound(PackageNotFoundException ex) {
        logger.info(ex.getMessage(), ex);
    }
}
