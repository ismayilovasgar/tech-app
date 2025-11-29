package com.ismayilov.techapp.util;

import com.ismayilov.techapp.dto.request.AccountToAccountRequestDTO;
import com.ismayilov.techapp.dto.response.CommonResponseDTO;
import com.ismayilov.techapp.dto.response.Status;
import com.ismayilov.techapp.dto.response.StatusCode;
import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.entity.TechUser;
import com.ismayilov.techapp.exception.ForbiddenAccountAccess;
import com.ismayilov.techapp.exception.InvalidAmount;
import com.ismayilov.techapp.exception.SameAccountTransfer;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AccountDTOUtil {

    @Autowired
    Logger logger;

    public void checkInvalidAmount(AccountToAccountRequestDTO accountToAccountRequestDTO) {
        if (accountToAccountRequestDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw InvalidAmount.builder()
                    .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.INVALID_AMOUNT)
                            .message("Amount is not correct")
                            .build()).build()).build();
        }
    }

    public void checkAccountNo(AccountToAccountRequestDTO accountToAccountRequestDTO) {
        if (accountToAccountRequestDTO.getCreditAccount().equals(accountToAccountRequestDTO.getDebitAccount())) {
            throw SameAccountTransfer.builder()
                    .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.SAME_ACCOUNT_TRANSFER)
                            .message("Credit and debit accounts cannot be the same.")
                            .build()).build()).build();
        }
    }

    public void verifyDebitAccountOwner(Account debitAccount, TechUser currentUser) {
        if (!debitAccount.getUser().getId().equals(currentUser.getId())) {
            throw ForbiddenAccountAccess.builder()
                    .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.FORBIDDEN_ACCOUNT_ACCESS)
                            .message("Debit account does not belong to the current user")
                            .build()).build()).build();
        }
    }


}
