package com.ismayilov.techapp.exception.token;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler(InvalidToken.class)
    public ResponseEntity<?> handleInvalidToken(InvalidToken ex) {
        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.FORBIDDEN);
    }
}