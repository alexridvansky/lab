package com.epam.esm.spring.web.config;

import com.epam.esm.spring.service.config.ServiceConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@Import(ServiceConfig.class)
@ComponentScan("com.epam.esm.spring.web")
public class WebConfig {

}
