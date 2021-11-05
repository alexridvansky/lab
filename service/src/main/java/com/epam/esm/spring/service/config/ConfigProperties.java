package com.epam.esm.spring.service.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "pagination")
@Getter
public class ConfigProperties {

    private int offsetDefault;
    private int limitDefault;
    private int limitMin;
    private int limitMax;
}