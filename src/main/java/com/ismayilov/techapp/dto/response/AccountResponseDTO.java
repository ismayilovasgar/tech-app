package com.ismayilov.techapp.dto.response;


import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.util.currency.Currency;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponseDTO implements Serializable {
    static final long serialVersionUID = 1L;

    BigDecimal balance;
    Currency currency;
    Boolean isActive;
    Integer accountNo;


    public static AccountResponseDTO fromEntity(Account account) {
        return AccountResponseDTO.builder()
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .isActive(account.getIsActive())
                .accountNo(account.getAccountNo())
                .build();
    }
}
