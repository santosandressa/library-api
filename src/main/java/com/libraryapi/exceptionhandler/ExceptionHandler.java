package com.libraryapi.exceptionhandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ErrorsException.Fields> fields = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            fields.add(new ErrorsException.Fields(name, message));

        }

        ErrorsException errors = new ErrorsException();
        errors.setStatus(status.value());
        errors.setTimestamp(OffsetDateTime.now());
        errors.setMessage("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente");
        errors.setFields(fields);
        return handleExceptionInternal(ex, errors, headers, status, request);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handle(RuntimeException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorsException errors = new ErrorsException();
        errors.setStatus(status.value());
        errors.setTimestamp(OffsetDateTime.now());
        errors.setMessage(ex.getMessage());
        return handleExceptionInternal(ex, errors, new HttpHeaders(), status, request);
    }
}
