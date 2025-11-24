package com.ismayilov.techapp.repository.impl;


import com.ismayilov.techapp.entity.Account;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AccountRepositoryCustomImpl implements AccountRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Account> findActiveAccountsCustom(String pin) {
        String jpql = "SELECT a FROM Account a WHERE a.user.pin = :pin AND a.isActive = true";
        return em.createQuery(jpql, Account.class)
                .setParameter("pin", pin)
                .getResultList();
    }

}
