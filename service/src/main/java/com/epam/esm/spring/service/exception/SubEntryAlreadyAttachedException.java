package com.epam.esm.spring.service.exception;

public class SubEntryAlreadyAttachedException extends RuntimeException {
    private static final int ERROR_CODE = 40009;
    private final String details;

    public SubEntryAlreadyAttachedException(String details) {
        this.details = details;
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }

    public String getDetails() {
        return details;
    }
}
