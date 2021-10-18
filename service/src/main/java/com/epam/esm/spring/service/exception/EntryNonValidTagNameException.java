package com.epam.esm.spring.service.exception;

public class EntryNonValidTagNameException extends RuntimeException {
    private static final int ERROR_CODE = 40006;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
