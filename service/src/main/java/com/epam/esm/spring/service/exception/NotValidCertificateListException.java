package com.epam.esm.spring.service.exception;

public class NotValidCertificateListException extends RuntimeException {
    private static final int ERROR_CODE = 40012;

    public int getErrorCode() {
        return ERROR_CODE;
    }
}
