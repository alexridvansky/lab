package com.epam.esm.spring.service.exception;

public class EntryInUseException extends RuntimeException {
    private final int ERROR_CODE = 40902;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
