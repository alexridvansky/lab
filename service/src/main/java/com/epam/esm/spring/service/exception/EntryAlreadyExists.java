package com.epam.esm.spring.service.exception;

public class EntryAlreadyExists extends RuntimeException {
    public EntryAlreadyExists() {
    }

    public EntryAlreadyExists(String message) {
        super(message);
    }

    public EntryAlreadyExists(String message, Throwable cause) {
        super(message, cause);
    }

    public EntryAlreadyExists(Throwable cause) {
        super(cause);
    }
}
