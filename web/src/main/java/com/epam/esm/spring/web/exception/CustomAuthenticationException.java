package com.epam.esm.spring.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
    private static final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    private static final int ERROR_CODE = 40101;

    public CustomAuthenticationException(String msg) {
        super(msg);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
