package com.ismayilov.techapp.repository.inter;

import com.ismayilov.techapp.entity.TechUser;
import com.ismayilov.techapp.repository.impl.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<TechUser, Long>, UserRepositoryCustom {
    @Query("select  p from TechUser p join fetch p.accountList where p.pin=:pin")
    Optional<TechUser> findByPin(String pin);
}
