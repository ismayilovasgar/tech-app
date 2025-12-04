package com.ismayilov.techapp.service.impl.transfer;

import com.ismayilov.techapp.entity.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferProcessor {

    public void process(Account debit, Account credit, BigDecimal amount) {
        debit.setBalance(debit.getBalance().subtract(amount));
        credit.setBalance(credit.getBalance().add(amount));
    }
}

