package com.epam.esm.spring.service.exception;

public class EntryCreationException extends RuntimeException {
    public EntryCreationException() {
    }

    public EntryCreationException(String message) {
        super(message);
    }

    public EntryCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntryCreationException(Throwable cause) {
        super(cause);
    }
}
