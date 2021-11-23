package com.epam.esm.spring.web.security;

import com.epam.esm.spring.service.dto.UserDetailsDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class JwtUserSecurity {
    public boolean hasUserId(Authentication authentication, Long userId) {
        Object authenticationPrincipal = authentication.getPrincipal();

        if (authenticationPrincipal instanceof UserDetailsDto) {
            return ((UserDetailsDto) authenticationPrincipal).getId().equals(userId);
        } else {
            return false;
        }
    }
}
