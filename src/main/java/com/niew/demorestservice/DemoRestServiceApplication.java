package com.niew.demorestservice;

import com.niew.demorestservice.domain.Product;
import com.niew.demorestservice.domain.ProductPackage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class DemoRestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoRestServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(ProductPackageRepository repository) {
        return (args) -> {
            repository.save(new ProductPackage("XXX", "paczka z mieczami", Arrays.asList(new Product[]{new Product(2, "XXX", "Miecz", 123L), new Product(1, "YYY", "Szabla", 12L)})));
            repository.save(new ProductPackage("Pusta", "Pusta", new ArrayList<>(0)));
        };
    }


}
