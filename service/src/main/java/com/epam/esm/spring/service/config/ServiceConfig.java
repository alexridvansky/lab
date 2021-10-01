package com.epam.esm.spring.service.config;

import com.epam.esm.spring.repository.config.RepositoryConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RepositoryConfig.class)
@ComponentScan("com.epam.esm.spring.service")
public class ServiceConfig {

}
