package com.epam.esm.spring.web.exception;

import com.epam.esm.spring.service.exception.EntryAlreadyExistsException;
import com.epam.esm.spring.service.exception.EntryCreationException;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";

    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<Object> handleEntryNotFoundException(EntryNotFoundException e) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put(ERROR_MESSAGE, e.getMessage());
        response.put(ERROR_CODE, 40401);

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntryAlreadyExistsException.class)
    public ResponseEntity<Object> handleEntryAlreadyExistsException(EntryAlreadyExistsException e) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put(ERROR_MESSAGE, e.getMessage());
        response.put(ERROR_CODE, 40001);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntryCreationException.class)
    public ResponseEntity<Object> handleEntryAlreadyExistsException(EntryCreationException e) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put(ERROR_MESSAGE, e.getMessage());
        response.put(ERROR_CODE, 40002);

        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }
}
