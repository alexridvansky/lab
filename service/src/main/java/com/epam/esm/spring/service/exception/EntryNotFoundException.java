package com.epam.esm.spring.service.exception;

public class EntryNotFoundException extends RuntimeException {
    private static final int ERROR_CODE = 40401;
    private final String description;
    private final String items;

    public EntryNotFoundException(String description, String items) {
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
