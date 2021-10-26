package com.epam.esm.spring.service.exception;

public class SubEntryNotFoundException extends RuntimeException {
    private static final int ERROR_CODE = 40011;
    private final String details;

    public SubEntryNotFoundException(String details) {
        this.details = details;
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }

    public String getDetails() {
        return details;
    }
}
