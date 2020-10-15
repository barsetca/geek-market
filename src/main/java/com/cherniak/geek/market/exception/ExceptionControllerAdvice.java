package com.cherniak.geek.market.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        MarketError marketError = new MarketError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(marketError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleResourceCreationException(ResourceCreationException e) {
        MarketError marketError = new MarketError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(marketError, HttpStatus.BAD_REQUEST);
    }
}
