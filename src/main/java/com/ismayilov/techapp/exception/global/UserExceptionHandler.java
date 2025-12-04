//package com.ismayilov.techapp.exception.user;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class UserExceptionHandler {
//
//    @ExceptionHandler(UserAlreadyExist.class)
//    public ResponseEntity<?> handleUserAlreadyExist(UserAlreadyExist ex) {
//        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(NoSuchUserExist.class)
//    public ResponseEntity<?> handleNoSuchUserExist(NoSuchUserExist ex) {
//        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(value=ForbiddenAccountAccess.class)
//    public ResponseEntity<?> handleForbiddenAccountAccess(ForbiddenAccountAccess ex) {
//        return new ResponseEntity<>(ex.getResponseDTO(), HttpStatus.BAD_REQUEST);
//    }
//}
