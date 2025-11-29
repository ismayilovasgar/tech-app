package com.ismayilov.techapp.exception.global;

import com.ismayilov.techapp.dto.response.CommonResponseDTO;
import com.ismayilov.techapp.dto.response.Status;
import com.ismayilov.techapp.dto.response.StatusCode;
import com.ismayilov.techapp.exception.account.*;
import com.ismayilov.techapp.exception.user.ForbiddenAccountAccess;
import com.ismayilov.techapp.exception.user.NoSuchUserExist;
import com.ismayilov.techapp.exception.user.UserAlreadyExist;
import com.ismayilov.techapp.exception.validation.InvalidDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> internalError(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(
                CommonResponseDTO
                        .builder()
                        .status(Status.builder()
                                .statusCode(StatusCode.INTERNAL_ERROR)
                                .message("Internal Error")
                                .build()
                        )
                        .build()
                , HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(value = InvalidDTO.class)
    public ResponseEntity<?> invalidDTO(InvalidDTO invalidDTO) {
        return new ResponseEntity<>(invalidDTO.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserAlreadyExist.class)
    public ResponseEntity<?> userExist(UserAlreadyExist userAlreadyExist) {
        return new ResponseEntity<>(userAlreadyExist.getResponseDTO(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = NoSuchUserExist.class)
    public ResponseEntity<?> noFoundUser(NoSuchUserExist noSuchUserExist) {
        return new ResponseEntity<>(noSuchUserExist.getResponseDTO(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoActiveAccount.class)
    public ResponseEntity<?> noFoundActiveAccount(NoActiveAccount noActiveAccount) {
        return new ResponseEntity<>(noActiveAccount.getResponseDTO(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidAmount.class)
    public ResponseEntity<?> invalidAmount(InvalidAmount invalidAmount) {
        return new ResponseEntity<>(invalidAmount.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CreditAccountInactive.class)
    public ResponseEntity<?> creditAccountInactive(CreditAccountInactive creditAccountInactive) {
        return new ResponseEntity<>(creditAccountInactive.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DebitAccountInactive.class)
    public ResponseEntity<?> invalidAmount(DebitAccountInactive debitAccountInactive) {
        return new ResponseEntity<>(debitAccountInactive.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = InsufficientFunds.class)
    public ResponseEntity<?> invalidAmount(InsufficientFunds insufficientFunds) {
        return new ResponseEntity<>(insufficientFunds.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = AccountNotFound.class)
    public ResponseEntity<?> accountNotFound(AccountNotFound accountNotFound) {
        return new ResponseEntity<>(accountNotFound.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = SameAccountTransfer.class)
    public ResponseEntity<?> sameAccountTransfer(SameAccountTransfer sameAccountTransfer) {
        return new ResponseEntity<>(sameAccountTransfer.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ForbiddenAccountAccess.class)
    public ResponseEntity<?> forbiddenAccountAccess(ForbiddenAccountAccess forbiddenAccountAccess) {
        return new ResponseEntity<>(forbiddenAccountAccess.getResponseDTO(), HttpStatus.BAD_REQUEST);
    }

}
