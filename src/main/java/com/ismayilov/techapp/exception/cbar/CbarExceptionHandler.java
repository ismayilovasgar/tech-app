package com.ismayilov.techapp.exception.cbar;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CbarExceptionHandler {

    @ExceptionHandler(CbarRestException.class)
    public ResponseEntity<?> handleCbarException(CbarRestException ex) {
        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
