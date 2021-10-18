package com.epam.esm.spring.service.exception;

public class EntryNonValidNameException extends RuntimeException {
    private static final int ERROR_CODE = 40001;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
