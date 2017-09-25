package com.niew.demorestservice;


import com.niew.demorestservice.domain.ProductPackage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

/**
 * DB operations
 */
public interface ProductPackageRepository extends Repository<ProductPackage, Long> {

    @Query("select pp from ProductPackage pp left join fetch pp.products")
    List<ProductPackage> findAll();

    Optional<ProductPackage> findOne(Long id);

    void delete(ProductPackage deleted);

    ProductPackage save(ProductPackage saved);

}
