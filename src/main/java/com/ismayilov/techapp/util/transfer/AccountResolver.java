package com.ismayilov.techapp.util.transfer;

import com.ismayilov.techapp.dto.response.StatusCode;
import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.repository.inter.AccountRepository;
import com.ismayilov.techapp.util.factory.ExceptionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountResolver {

    private final AccountRepository accountRepository;

    public Account getDebitAccount(String accountNo) {
        return accountRepository.findByAccountNo(Integer.valueOf(accountNo))
                .orElseThrow(() ->
                        ExceptionFactory.accountNotFound(
                                StatusCode.DEBIT_ACCOUNT_NOT_PRESENT,
                                "Debit account is not present"));
    }

    public Account getCreditAccount(String accountNo) {
        return accountRepository.findByAccountNo(Integer.valueOf(accountNo))
                .orElseThrow(() ->
                        ExceptionFactory.accountNotFound(
                                StatusCode.CREDIT_ACCOUNT_NOT_PRESENT,
                                "Credit account is not present"));
    }
}

