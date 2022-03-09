package com.bondar.apiforshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ApiForShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiForShopApplication.class, args);
    }

}
