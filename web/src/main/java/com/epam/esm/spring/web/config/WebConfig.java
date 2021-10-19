package com.epam.esm.spring.web.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan("com.epam.esm.spring")
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public ResourceBundleMessageSource getResourceBundleMessageSource() {
        ResourceBundleMessageSource messages = new ResourceBundleMessageSource();
        messages.addBasenames("error_messages");
        messages.setDefaultEncoding("UTF-8");
        return messages;
    }

    public static void main(String[] args) {
        SpringApplication.run(WebConfig.class, args);
    }
}
