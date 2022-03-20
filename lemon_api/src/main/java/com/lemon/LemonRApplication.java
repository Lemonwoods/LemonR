package com.lemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LemonRApplication {
    public static void main(String[] args) {
        SpringApplication.run(LemonRApplication.class, args);
    }
}
