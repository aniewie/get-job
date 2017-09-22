package com.niew.demorestservice;


import com.niew.demorestservice.domain.ProductPackage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ProductPackageRepository extends CrudRepository<ProductPackage, Long> {

    List<ProductPackage> findAll();
}
