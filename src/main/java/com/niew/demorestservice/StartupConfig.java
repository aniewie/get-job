package com.niew.demorestservice;

import com.niew.demorestservice.domain.Product;
import com.niew.demorestservice.domain.ProductPackage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Insert some examples to DB
 */
@Configuration
public class StartupConfig {
    @Bean
   public CommandLineRunner demo(ProductPackageRepository repository) {
        return (args) -> {
            repository.save(new ProductPackage("First package", "Weapon", Arrays.asList(new Product[]{new Product(2, "7Hv0hA2nmci7", "Knife", 349L), new Product(1, "7dgX6XzU3Wds", "Sword", 899L)})));
            repository.save(new ProductPackage("Second package", "Money", Arrays.asList(new Product[]{new Product(1, "500R5EHvNlNB", "Gold coin", 249L)})));
        };
    }
}
