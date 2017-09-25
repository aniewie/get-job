package com.niew.demorestservice;

import com.niew.demorestservice.dto.ProductPackageDataIn;
import com.niew.demorestservice.dto.ProductPackageDataOut;
import com.niew.demorestservice.exception.ExchangeRateNotFoundException;
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

    /**
     * Retrieves all packages. Price in USD, but in units (dollars, nto cents)
     * @return - list of packages
     */
    @RequestMapping(method = RequestMethod.GET)
    List<ProductPackageDataOut> listAllPackages() {
        return service.listAllPackages();
    }

    /**
     * Returns data of a package with the given id with price in currency from parameter (in units of a given currency)
     * Currency rates taken from http://api.fixer.io/latest
     *
     * @param id - id of a package
     * @param currency - ISO currency code
     * @return - package with price in a given currency
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ProductPackageDataOut retrievePackage(@PathVariable("id") Long id, @RequestParam(value="currency", defaultValue="USD") String currency) {
        return service.retrievePackage(id, currency);
    }

    /**
     * Deleted package with given id, returning its content
     * @param id - id of a package to be deleted
     * @return - deleted package
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ProductPackageDataOut deletePackage(@PathVariable("id") Long id) {
        return service.deletePackage(id);
    }

    /**
     * Creates new package.
     * Ignores price and name of product from the input, instead consulting product-service.herokuapp.com API (cached after first invocation) for these params
     * Allows only valid products (listed at product-service.herokuapp.com API)
     * Allows duplicated products (more items of the same type), but "compacts" them while saving (summing counts)
     * @param data - data of a package with products to be created
     * @return - created package
     */
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST)
    ProductPackageDataOut createPackage(@RequestBody @Valid ProductPackageDataIn data) {
        return service.createPackage(data);
    }

    /**
     * Updates content of a package
     * Allows change of name and description of package
     * Allows adding new products
     * Allows removing products
     * Allows modification of product count
     * Ignores price and name of product from the input, instead consulting product-service.herokuapp.com API (cached) for these params
     * Each package update sets current values of name and price of each product in package (regardless of whether it was changes or not)
     * Allows only valid products (listed at product-service.herokuapp.com API)
     * Allows duplicated products (more items of the same type), but "compacts" them while saving (summing counts)
     * @param id - id of a package to be modified
     * @param data - data of a modified package (with possibly added, modified, removed products)
     * @return - saved modified package
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    ProductPackageDataOut updatePackage(@PathVariable("id") Long id, @RequestBody @Valid ProductPackageDataIn data) {
        return service.updatePackage(id, data);
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No package found")
    public void handlePackageNotFound(PackageNotFoundException ex) {
        logger.info(ex.getMessage(), ex);
    }
}
