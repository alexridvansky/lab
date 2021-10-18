package com.epam.esm.spring.service.exception;

public class EntryNonValidDurationException extends RuntimeException {
    private static final int ERROR_CODE = 40005;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
