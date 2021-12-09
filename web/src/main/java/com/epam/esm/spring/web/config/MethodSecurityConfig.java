package com.epam.esm.spring.web.config;

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,      // * unnecessary in this particular case, was user for playing within different settings
        jsr250Enabled = true)      // * unnecessary in this particular case, was user for playing within different settings
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
}