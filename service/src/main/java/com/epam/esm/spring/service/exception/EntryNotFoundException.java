package com.epam.esm.spring.service.exception;

public class EntryNotFoundException extends RuntimeException {
    private static final int ERROR_CODE = 40401;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
