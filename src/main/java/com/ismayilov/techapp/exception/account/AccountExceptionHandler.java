package com.ismayilov.techapp.exception.account;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccountExceptionHandler {

    @ExceptionHandler(NoActiveAccount.class)
    public ResponseEntity<?> handleNoActiveAccount(NoActiveAccount ex) {
        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidAmount.class)
    public ResponseEntity<?> handleInvalidAmount(InvalidAmount ex) {
        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreditAccountInactive.class)
    public ResponseEntity<?> handleCreditAccountInactive(CreditAccountInactive ex) {
        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DebitAccountInactive.class)
    public ResponseEntity<?> handleDebitAccountInactive(DebitAccountInactive ex) {
        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientFunds.class)
    public ResponseEntity<?> handleInsufficientFunds(InsufficientFunds ex) {
        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountNotFound.class)
    public ResponseEntity<?> handleAccountNotFound(AccountNotFound ex) {
        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameAccountTransfer.class)
    public ResponseEntity<?> handleSameAccountTransfer(SameAccountTransfer ex) {
        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ForbiddenAccountAccess.class)
    public ResponseEntity<?> handleForbiddenAccountAccess(ForbiddenAccountAccess ex) {
        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }
}

