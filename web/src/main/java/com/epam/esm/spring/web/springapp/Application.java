package com.epam.esm.spring.web.springapp;

import com.epam.esm.spring.web.config.WebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(WebConfig.class, args);
    }
}
