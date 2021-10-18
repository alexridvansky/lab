package com.epam.esm.spring.service.exception;

public class NotValidSearchRequest extends RuntimeException {
    private static final int ERROR_CODE = 40007;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
