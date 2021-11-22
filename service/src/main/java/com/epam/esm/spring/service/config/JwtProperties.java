package com.epam.esm.spring.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:jwt.properties")
@ConfigurationProperties("jwt")
@Getter
@Setter
public class JwtProperties {

    private String secret;
    private int expiration;
    private String header;
}