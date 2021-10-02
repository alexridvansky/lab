package com.epam.esm.spring.service.exception;

public class EntryAlreadyExistsException extends RuntimeException {
    public EntryAlreadyExistsException() {
    }

    public EntryAlreadyExistsException(String message) {
        super(message);
    }

    public EntryAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntryAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
