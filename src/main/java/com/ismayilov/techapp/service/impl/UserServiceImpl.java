package com.ismayilov.techapp.service.impl;

import com.ismayilov.techapp.dto.request.AuthenticationRequestDTO;
import com.ismayilov.techapp.dto.request.UserRequestDTO;
import com.ismayilov.techapp.dto.response.CommonResponseDTO;
import com.ismayilov.techapp.dto.response.Status;
import com.ismayilov.techapp.dto.response.StatusCode;
import com.ismayilov.techapp.dto.response.UserResponseDTO;
import com.ismayilov.techapp.entity.TechUser;
import com.ismayilov.techapp.exception.NoSuchUserExist;
import com.ismayilov.techapp.exception.UserAlreadyExist;
import com.ismayilov.techapp.repository.inter.UserRepository;
import com.ismayilov.techapp.service.inter.UserService;
import com.ismayilov.techapp.util.DTOUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    @Autowired
    DTOUtil dtoUtil;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    public CommonResponseDTO<?> saveUser(UserRequestDTO userRequestDTO) {
        dtoUtil.isValid(userRequestDTO);
        if (userRepository.findByPin(userRequestDTO.getPin()).isPresent()) {
            throw UserAlreadyExist.builder().responseDTO(
                    CommonResponseDTO.builder()
                            .status(Status.builder()
                                    .statusCode(StatusCode.USER_EXIST)
                                    .message("User with pin: " + userRequestDTO.getPin()
                                            + "is exist. Please enter a pin that has not been registered before"
                                    )
                                    .build()
                            ).build()
            ).build();
        }

        TechUser user = TechUser.builder()
                .name(userRequestDTO.getName())
                .surname(userRequestDTO.getSurname())
                .pin(userRequestDTO.getPin())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .role("ROLE_USER")
                .build();

        user.addAccountToList(userRequestDTO.getAccountRequestDTOList());
        return CommonResponseDTO.builder()
                .status(Status.builder()
                        .statusCode(StatusCode.SUCCESS)
                        .message("User created Successfully")
                        .build())
                .data(UserResponseDTO.entityResponse(userRepository.save(user)))
                .build();
    }

    public CommonResponseDTO<?> loginUser(AuthenticationRequestDTO authenticationRequestDTO) {
        dtoUtil.isValid(authenticationRequestDTO);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequestDTO.getPassword(), authenticationRequestDTO.getPassword()
            ));
        } catch (Exception e) {
            throw NoSuchUserExist.builder().responseDTO(
                    CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.USER_NOT_EXIST)
                            .message("pin: " + authenticationRequestDTO.getPin() + " or password "
                                    + authenticationRequestDTO.getPassword() + " is wrong")
                            .build()
                    ).build()
            ).build();
        }

        return CommonResponseDTO.builder().data(authenticationRequestDTO).status(Status.builder()
                .statusCode(StatusCode.SUCCESS)
                .message("Welcome to our FIN-TECH Application")
                .build()).build();
    }
}
