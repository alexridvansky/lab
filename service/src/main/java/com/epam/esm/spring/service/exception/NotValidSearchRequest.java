package com.epam.esm.spring.service.exception;

public class NotValidSearchRequest extends RuntimeException {
    private static final int ERROR_CODE = 40007;
    private final String description;

    public NotValidSearchRequest(String description) {
        this.description = description;
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }

    public String getDescription() {
        return description;
    }
}
