package com.epam.esm.spring.service.exception;

public class EntryCreationException extends RuntimeException {
    private final int ERROR_CODE = 40001;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
