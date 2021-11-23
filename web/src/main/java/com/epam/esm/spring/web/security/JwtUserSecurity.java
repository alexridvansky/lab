package com.epam.esm.spring.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUserSecurity {
    public boolean hasUserId(Authentication authentication, Long userId) {
        return authentication.getPrincipal().equals(userId);
    }
}
