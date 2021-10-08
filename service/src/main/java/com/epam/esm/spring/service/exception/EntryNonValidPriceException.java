package com.epam.esm.spring.service.exception;

public class EntryNonValidPriceException extends RuntimeException {
    private static final int ERROR_CODE = 40004;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
