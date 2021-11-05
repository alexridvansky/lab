package com.epam.esm.spring.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan("com.epam.esm.spring")
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public ResourceBundleMessageSource getResourceBundleMessageSource() {
        ResourceBundleMessageSource messages = new ResourceBundleMessageSource();
        messages.addBasenames("error_messages");
        messages.setDefaultEncoding("UTF-8");
        return messages;
    }
}
