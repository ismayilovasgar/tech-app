package com.ismayilov.techapp.exception.validation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(InvalidDTO.class)
    public ResponseEntity<?> handleInvalidDTO(InvalidDTO ex) {
        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }
}
