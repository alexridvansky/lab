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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@RestControllerAdvice
public class ControllerAdvisor {
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_CODE = "errorCode";
    private static final String ERROR_DESCRIPTION = "errorDescription";
    private static final int NOT_VALID_REQUEST_CODE = 40000;
    private static final int NOT_VALID_EXCEPTION_CODE = 40008;
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
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e, Locale locale) {
        return new ResponseEntity<>(createResponse(NOT_VALID_REQUEST_CODE, locale, e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, Locale locale) {
        return new ResponseEntity<>(createResponse(NOT_VALID_REQUEST_CODE, locale, e.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, Locale locale) {
        String message = resolveBindingResultErrors(e.getBindingResult());

        return new ResponseEntity<>(createResponse(NOT_VALID_EXCEPTION_CODE, locale, message), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> createResponse(int errorCode, Locale locale) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put(ERROR_MESSAGE, messages.getMessage(getMessageByCode(errorCode), null, locale));
        response.put(ERROR_CODE, errorCode);

        return response;
    }

    private Map<String, Object> createResponse(int errorCode, Locale locale, String errorDescription) {
        Map<String, Object> response = createResponse(errorCode, locale);
        response.put(ERROR_DESCRIPTION, errorDescription);

        return response;
    }

    private String getMessageByCode(int errorCode) {
        return "error_msg." + errorCode;
    }

    private String resolveBindingResultErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(fr -> {
                    String field = fr.getField();
                    String validationMessage = fr.getDefaultMessage();
                    return format("'%s': %s", field, validationMessage);
                })
                .collect(joining(", "));
    }
}
