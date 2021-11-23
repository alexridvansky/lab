package com.epam.esm.spring.service.exception;

public class EntryAlreadyExistsException extends RuntimeException {
    private static final int ERROR_CODE = 40901;
    private final String details;

    public EntryAlreadyExistsException(String details) {
        this.details = details;
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }

    public String getDetails() {
        return details;
    }
}
