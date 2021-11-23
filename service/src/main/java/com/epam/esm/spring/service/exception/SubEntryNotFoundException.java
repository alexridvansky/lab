package com.epam.esm.spring.service.exception;

public class SubEntryNotFoundException extends RuntimeException {
    private static final int ERROR_CODE = 40011;
    private final String description;
    private final String items;

    public SubEntryNotFoundException(String description, String items) {
        this.description = description;
        this.items = items;
    }

    public int getErrorCode() {
        return ERROR_CODE;
    }

    public String getDescription() {
        return description;
    }

    public String getItems() {
        return items;
    }
}
