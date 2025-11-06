package com.ismayilov.techapp.repository.inter;

import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.repository.impl.AccountRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>, AccountRepositoryCustom {
}
