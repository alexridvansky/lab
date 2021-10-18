package com.epam.esm.spring.service.exception;

public class EntryNonValidRequestException extends RuntimeException {
    private static final int ERROR_CODE = 40003;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
