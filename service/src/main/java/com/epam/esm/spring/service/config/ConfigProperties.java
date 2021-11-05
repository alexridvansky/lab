package com.epam.esm.spring.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:pagination.properties")
@ConfigurationProperties("pagination")
@Getter
@Setter
public class ConfigProperties {

    private Integer page;
    private Integer size;
}