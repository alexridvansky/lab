package com.epam.esm.spring.service.exception;

public class EntryNonValidDescriptionException extends RuntimeException {
    private static final int ERROR_CODE = 40002;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
