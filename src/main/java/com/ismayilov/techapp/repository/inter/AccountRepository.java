package com.ismayilov.techapp.repository.inter;

import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.repository.impl.AccountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {


    List<Account> findByUserPinAndIsActiveTrue(String pin);


    @Query("SELECT a FROM Account a WHERE a.user.pin = :pin AND a.isActive = true")
    List<Account> findActiveAccountsByUserPin(@Param("pin") String pin);

//    Optional<Account> findByAccountNo(Integer accountNo);
//    boolean existsByAccountNoAndUserId(Integer  accountNumber, Long userId);

    Optional<Account> findByAccountNo(Integer accountNo);
}
