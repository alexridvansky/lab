package com.epam.esm.spring.service.exception;

import java.util.Set;

public class EntityIntersectionException extends RuntimeException {
    private static final int ERROR_CODE = 40010;
    private final Set<String> items;

    public EntityIntersectionException(Set<String> items) {
        this.items = items;
    }
    public int getErrorCode() {
        return ERROR_CODE;
    }

    public String getItems() {
        return items.toString();
    }
}
