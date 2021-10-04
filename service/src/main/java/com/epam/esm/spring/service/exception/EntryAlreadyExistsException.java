package com.epam.esm.spring.service.exception;

public class EntryAlreadyExistsException extends RuntimeException {
    private final int ERROR_CODE = 40901;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
