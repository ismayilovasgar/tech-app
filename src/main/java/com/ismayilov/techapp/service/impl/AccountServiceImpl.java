package com.ismayilov.techapp.service.impl;

import com.ismayilov.techapp.dto.request.AccountToAccountRequestDTO;
import com.ismayilov.techapp.dto.response.AccountResponseDTOList;
import com.ismayilov.techapp.dto.response.CommonResponseDTO;
import com.ismayilov.techapp.dto.response.Status;
import com.ismayilov.techapp.dto.response.StatusCode;
import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.entity.TechUser;
import com.ismayilov.techapp.exception.InvalidAmount;
import com.ismayilov.techapp.exception.SameAccountTransfer;
import com.ismayilov.techapp.repository.impl.AccountRepositoryCustomImpl;
import com.ismayilov.techapp.repository.inter.AccountRepository;
import com.ismayilov.techapp.repository.inter.UserRepository;
import com.ismayilov.techapp.service.inter.AccountService;
import com.ismayilov.techapp.util.AccountDTOUtil;
import com.ismayilov.techapp.util.CurrentUser;
import com.ismayilov.techapp.util.DTOUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import java.util.Optional;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountServiceImpl implements AccountService {

    @Autowired
    CurrentUser currentUser;

    @Autowired
    DTOUtil dtoUtil;

    @Autowired
    AccountDTOUtil accountDTOUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;


    public CommonResponseDTO<?> getAccount() {
        Optional<TechUser> user = userRepository.findByPin(currentUser.getCurrentUser().getUsername());

        return CommonResponseDTO.builder()
                .status(Status.builder()
                        .statusCode(StatusCode.SUCCESS)
                        .message("Accounts successfully fetched")
                        .build())
                .data(AccountResponseDTOList.entityToDTO(user.get().getAccountList()))
                .build();
    }

    @Override
    public CommonResponseDTO<?> getActiveAccounts() {
        Optional<TechUser> user = userRepository.findByPin(currentUser.getCurrentUser().getUsername());


        var activeAccounts = user.get().getAccountList()
                .stream()
                .filter(account -> Boolean.TRUE.equals(account.getIsActive()))
                .collect(Collectors.toList());

        return CommonResponseDTO.builder()
                .status(Status.builder()
                        .statusCode(StatusCode.SUCCESS)
                        .message("Active accounts successfully fetched")
                        .build())
                .data(AccountResponseDTOList.entityToDTO(activeAccounts))
                .build();
    }

    public CommonResponseDTO<?> account2account(AccountToAccountRequestDTO accountToAccountRequestDTO) {
        dtoUtil.isValid(accountToAccountRequestDTO);
        accountDTOUtil.checkAccountNo(accountToAccountRequestDTO);
        accountDTOUtil.checkInvalidAmount(accountToAccountRequestDTO);

//        if (accountToAccountRequestDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
//            throw InvalidAmount.builder()
//                    .responseDTO(CommonResponseDTO.builder().status(Status.builder()
//                            .statusCode(StatusCode.INVALID_AMOUNT)
//                            .message("Amount is not correct")
//                            .build()).build()).build();
//        } else if (accountToAccountRequestDTO.getCreditAccount().equals(accountToAccountRequestDTO.getDebitAccount())) {
//            throw SameAccountTransfer.builder()
//                    .responseDTO(CommonResponseDTO.builder().status(Status.builder()
//                            .statusCode(StatusCode.SAME_ACCOUNT_TRANSFER)
//                            .message("Credit and debit accounts cannot be the same.")
//                            .build()).build()).build();
//        }

        Optional<Account> byDebitAccountNo = accountRepository.findByAccountNo(accountToAccountRequestDTO.getDebitAccount());
        return null;
    }
}
