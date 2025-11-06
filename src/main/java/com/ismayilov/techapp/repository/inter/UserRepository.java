package com.ismayilov.techapp.repository.inter;

import com.ismayilov.techapp.entity.TechUser;
import com.ismayilov.techapp.repository.impl.UserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TechUser, Long>, UserRepositoryCustom {
}
