package com.ismayilov.techapp.repository.inter;

import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.repository.impl.AccountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {

    // Sadə JPA method query
    List<Account> findByUserPinAndIsActiveTrue(String pin);

    // Əgər lazımdırsa @Query ilə də yaza bilərsən
    @Query("SELECT a FROM Account a WHERE a.user.pin = :pin AND a.isActive = true")
    List<Account> findActiveAccountsByUserPin(@Param("pin") String pin);


}
