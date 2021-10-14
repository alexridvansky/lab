package com.epam.esm.spring.web.exception;

import com.epam.esm.spring.service.exception.EntryAlreadyExistsException;
import com.epam.esm.spring.service.exception.EntryNonValidDurationException;
import com.epam.esm.spring.service.exception.EntryNonValidNameException;
import com.epam.esm.spring.service.exception.EntryNonValidPriceException;
import com.epam.esm.spring.service.exception.EntryNonValidRequestException;
import com.epam.esm.spring.service.exception.EntryNonValidTagNameException;
import com.epam.esm.spring.service.exception.EntryNotFoundException;
import com.epam.esm.spring.service.exception.NotValidSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
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

    @ExceptionHandler(EntryNonValidNameException.class)
    public ResponseEntity<Object> handleEntryNonValidNameException(EntryNonValidNameException e, Locale locale) {
        return new ResponseEntity<>(createResponse(e.getErrorCode(), locale), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntryNonValidRequestException.class)
    public ResponseEntity<Object> handleEntryBadRequestException(EntryNonValidRequestException e, Locale locale) {
        return new ResponseEntity<>(createResponse(e.getErrorCode(), locale), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntryNonValidPriceException.class)
    public ResponseEntity<Object> handleEntryNonValidPriceException(EntryNonValidPriceException e, Locale locale) {
        return new ResponseEntity<>(createResponse(e.getErrorCode(), locale), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntryNonValidDurationException.class)
    public ResponseEntity<Object> handleEntryNonValidDurationException(EntryNonValidDurationException e, Locale locale) {
        return new ResponseEntity<>(createResponse(e.getErrorCode(), locale), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntryNonValidTagNameException.class)
    public ResponseEntity<Object> handleEntryNonValidTagNameException(EntryNonValidTagNameException e, Locale locale) {
        return new ResponseEntity<>(createResponse(e.getErrorCode(), locale), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotValidSearchRequest.class)
    public ResponseEntity<Object> handleNotValidSearchRequest(NotValidSearchRequest e, Locale locale) {
        return new ResponseEntity<>(createResponse(e.getErrorCode(), locale), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Object> handleConstraintViolationException(ConstraintViolationException e, Locale locale) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(ERROR_MESSAGE, e.getMessage());

        return response;
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
