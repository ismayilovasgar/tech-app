package com.ismayilov.techapp.util.factory;

import com.ismayilov.techapp.dto.response.StatusCode;
import com.ismayilov.techapp.exception.account.AccountNotFound;
import com.ismayilov.techapp.exception.account.CreditAccountInactive;
import com.ismayilov.techapp.exception.account.DebitAccountInactive;
import com.ismayilov.techapp.exception.account.InsufficientFunds;
import com.ismayilov.techapp.exception.account.ForbiddenAccountAccess;
import com.ismayilov.techapp.exception.token.InvalidToken;


public class ExceptionFactory {

    public static AccountNotFound accountNotFound(StatusCode code, String msg) {
        return AccountNotFound.builder()
                .responseDTO(ResponseFactory.error(code, msg))
                .build();
    }

    public static DebitAccountInactive debitInactive() {
        return DebitAccountInactive.builder()
                .responseDTO(ResponseFactory.error(StatusCode.DEBIT_ACCOUNT_INACTIVE,
                        "Debit account is inactive"))
                .build();
    }

    public static CreditAccountInactive creditInactive() {
        return CreditAccountInactive.builder()
                .responseDTO(ResponseFactory.error(StatusCode.CREDIT_ACCOUNT_INACTIVE,
                        "Credit account is inactive"))
                .build();
    }

    public static InsufficientFunds insufficientDebit() {
        return InsufficientFunds.builder()
                .responseDTO(ResponseFactory.error(StatusCode.INSUFFICIENT_DEBIT_BALANCE,
                        "Debit balance is not enough"))
                .build();
    }

    public static InsufficientFunds insufficientCredit() {
        return InsufficientFunds.builder()
                .responseDTO(ResponseFactory.error(StatusCode.INSUFFICIENT_CREDIT_BALANCE,
                        "Credit balance is not enough !!"))
                .build();
    }

    public static InvalidToken invalidToken() {
        return InvalidToken.builder()
                .responseDTO(ResponseFactory.error(StatusCode.INVALID_TOKEN,
                        "The token is not tied to this user"))
                .build();
    }

    public static ForbiddenAccountAccess forbiddenAccountAccess() {
        return ForbiddenAccountAccess.builder()
                .responseDTO(ResponseFactory.error(StatusCode.FORBIDDEN_ACCOUNT_ACCESS,
                        "Debit account does not belong to the current user"))
                .build();
    }

}
