package com.epam.esm.spring.web.exception;

import com.epam.esm.spring.service.exception.EntryAlreadyExistsException;
import com.epam.esm.spring.service.exception.EntryCreationException;
import com.epam.esm.spring.service.exception.EntryInUseException;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";
    private final ResourceBundleMessageSource messages;

    @Autowired
    public ControllerAdvisor(ResourceBundleMessageSource messages) {
        this.messages = messages;
    }

    @ExceptionHandler(EntryNotFoundException.class)
    public ResponseEntity<Object> handleEntryNotFoundException(EntryNotFoundException e, Locale locale) {
       return new ResponseEntity<>(createResponse(e.getErrorCode(), locale), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntryAlreadyExistsException.class)
    public ResponseEntity<Object> handleEntryAlreadyExistsException(EntryAlreadyExistsException e, Locale locale) {
        return new ResponseEntity<>(createResponse(e.getErrorCode(), locale), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntryInUseException.class)
    public ResponseEntity<Object> handleEntryAlreadyExistsException(EntryInUseException e, Locale locale) {
        return new ResponseEntity<>(createResponse(e.getErrorCode(), locale), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntryCreationException.class)
    public ResponseEntity<Object> handleEntryAlreadyExistsException(EntryCreationException e, Locale locale) {
        return new ResponseEntity<>(createResponse(e.getErrorCode(), locale), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> createResponse(int errorCode, Locale locale) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(ERROR_MESSAGE, messages.getMessage(getMessageByCode(errorCode), null, locale));
        response.put(ERROR_CODE, errorCode);

        return response;
    }

    private String getMessageByCode(int errorCode) {
        return "error_msg." + errorCode;
    }
}
