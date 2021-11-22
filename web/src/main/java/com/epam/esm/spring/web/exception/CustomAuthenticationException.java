package com.epam.esm.spring.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
    private final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public CustomAuthenticationException(String msg) {
        super(msg);
    }
}
