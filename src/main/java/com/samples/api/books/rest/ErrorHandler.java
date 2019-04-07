package com.samples.api.books.rest;

import com.samples.api.books.internal.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.samples.api.books.rest.BookResponseWithMessage.error;
import static com.samples.api.books.rest.BookResponseWithMessage.notFound;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        log.warn("Returning HTTP 400 Bad Request", e);
        return ResponseEntity.badRequest().body(error(400, "bad request", e.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public BookResponseWithMessage handleNotFound(RuntimeException ex) {
        return notFound(ex.getMessage());
    }

}