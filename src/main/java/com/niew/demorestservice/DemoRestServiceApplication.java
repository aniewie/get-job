package com.niew.demorestservice;

import com.niew.demorestservice.domain.ProductPackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoRestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRestServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ProductPackageRepository repository) {
		return (args) -> {
			repository.save(new ProductPackage("ziolo"));
			repository.save(new ProductPackage("Miecz"));
		};
	}
}
