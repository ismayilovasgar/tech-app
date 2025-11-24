package com.ismayilov.techapp.repository.impl;

import com.ismayilov.techapp.entity.Account;

import java.util.List;

public interface AccountRepositoryCustom {
    List<Account> findActiveAccountsCustom(String pin);
}
