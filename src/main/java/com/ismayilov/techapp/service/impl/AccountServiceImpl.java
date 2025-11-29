package com.ismayilov.techapp.service.impl;

import com.ismayilov.techapp.config.security.UserDetailsImpl;
import com.ismayilov.techapp.dto.request.AccountToAccountRequestDTO;
import com.ismayilov.techapp.dto.response.*;
import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.entity.TechUser;
import com.ismayilov.techapp.exception.account.AccountNotFound;
import com.ismayilov.techapp.exception.account.CreditAccountInactive;
import com.ismayilov.techapp.exception.account.DebitAccountInactive;
import com.ismayilov.techapp.exception.account.InsufficientFunds;
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

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;


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

    @Transactional
    public CommonResponseDTO<?> account2account(AccountToAccountRequestDTO accountToAccountRequestDTO) {
        dtoUtil.isValid(accountToAccountRequestDTO);
        accountDTOUtil.checkInvalidAmount(accountToAccountRequestDTO);
        accountDTOUtil.checkAccountNo(accountToAccountRequestDTO);

        Optional<Account> byDebitAccountNo = accountRepository.findByAccountNo(accountToAccountRequestDTO.getDebitAccount());
        TechUser user = ((UserDetailsImpl) currentUser.getCurrentUser()).getTechUser();

        Account debitAccount;
        Account creditAccount;

        if (byDebitAccountNo.isPresent()) {
            debitAccount = byDebitAccountNo.get();
            accountDTOUtil.verifyDebitAccountOwner(debitAccount, user);

            if (!debitAccount.getIsActive()) {
                throw DebitAccountInactive.builder()
                        .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                                .statusCode(StatusCode.DEBIT_ACCOUNT_INACTIVE)
                                .message("Debit account is inactive")
                                .build()).build()).build();
            }
            if (debitAccount.getBalance().compareTo(accountToAccountRequestDTO.getAmount()) < 0) {
                throw InsufficientFunds.builder()
                        .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                                .statusCode(StatusCode.INSUFFICIENT_DEBIT_BALANCE)
                                .message("Debit balance is not enough")
                                .build()).build()).build();
            }


            Optional<Account> byCreditAccountNo = accountRepository.findByAccountNo(accountToAccountRequestDTO.getCreditAccount());

            if (byCreditAccountNo.isPresent()) {
                creditAccount = byCreditAccountNo.get();
                if (!creditAccount.getIsActive()) {
                    throw CreditAccountInactive.builder()
                            .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                                    .statusCode(StatusCode.CREDIT_ACCOUNT_INACTIVE)
                                    .message("Credit account is inactive")
                                    .build()).build()).build();
                }
                if (creditAccount.getBalance().compareTo(accountToAccountRequestDTO.getAmount()) < 0) {
                    throw InsufficientFunds.builder()
                            .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                                    .statusCode(StatusCode.INSUFFICIENT_CREDIT_BALANCE)
                                    .message("Credit balance is not enough")
                                    .build()).build()).build();
                }
            } else {
                throw AccountNotFound.builder()
                        .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                                .statusCode(StatusCode.CREDIT_ACCOUNT_NOT_PRESENT)
                                .message("Credit balance is not present")
                                .build()).build()).build();
            }
        } else {
            throw AccountNotFound.builder()
                    .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.DEBIT_ACCOUNT_NOT_PRESENT)
                            .message("Debit balance is not present")
                            .build()).build()).build();
        }


        debitAccount.setBalance(debitAccount.getBalance().subtract(accountToAccountRequestDTO.getAmount()));
        creditAccount.setBalance(creditAccount.getBalance().add(accountToAccountRequestDTO.getAmount()));

        System.out.println(debitAccount.getBalance());
        System.out.println(creditAccount.getBalance());

        return CommonResponseDTO.builder()
                .status(Status.builder()
                        .statusCode(StatusCode.SUCCESS)
                        .message("Transfer completed successfully").build())
                .data(AccountResponseDTO.builder()
                        .balance(debitAccount.getBalance())
                        .currency(debitAccount.getCurrency())
                        .isActive(debitAccount.getIsActive())
                        .accountNo(debitAccount.getAccountNo()).build()).build();
    }
}
