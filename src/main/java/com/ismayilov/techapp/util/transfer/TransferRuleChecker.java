package com.ismayilov.techapp.util.transfer;

import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.util.factory.ExceptionFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferRuleChecker {

    public void checkDebitAccount(Account debit, BigDecimal amount) {

        if (!debit.getIsActive()) {
            throw ExceptionFactory.debitInactive();
        }

        if (debit.getBalance().compareTo(amount) < 0) {
            throw ExceptionFactory.insufficientDebit();
        }
    }

    public void checkCreditAccount(Account credit, BigDecimal amount) {

        if (!credit.getIsActive()) {
            throw ExceptionFactory.creditInactive();
        }

        // kredit balansı yoxlanmalı DEYİL — bank sistemi balansın az olmasını problem etmir
        // lakin səndə belə şərt qoyulub, saxlayırıq
        if (credit.getBalance().compareTo(amount) < 0) {
            throw ExceptionFactory.insufficientCredit();
        }
    }
}
