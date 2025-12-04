package com.ismayilov.techapp.config.security;

import com.ismayilov.techapp.dto.response.CommonResponseDTO;
import com.ismayilov.techapp.dto.response.Status;
import com.ismayilov.techapp.dto.response.StatusCode;
import com.ismayilov.techapp.entity.TechUser;
import com.ismayilov.techapp.exception.global.NoSuchUserExist;
import com.ismayilov.techapp.repository.inter.UserRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Logger logger;

    @Override
    public UserDetails loadUserByUsername(String pin) throws UsernameNotFoundException {
        Optional<TechUser> byPin = userRepository.findByPin(pin);
        if (byPin.isPresent()) {
            return new UserDetailsImpl(byPin.get());
        } else {
            logger.error("There is no user with pin: {}", pin);
            throw NoSuchUserExist.builder().responseDTO(
                    CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.USER_NOT_EXIST)
                            .message("There is no user with pin: " + pin).build()
                    ).build()
            ).build();
        }
    }

}
