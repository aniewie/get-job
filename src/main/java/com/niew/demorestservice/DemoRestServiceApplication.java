package com.niew.demorestservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DemoRestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoRestServiceApplication.class, args);
    }

}
