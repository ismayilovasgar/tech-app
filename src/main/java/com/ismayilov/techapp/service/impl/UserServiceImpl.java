package com.ismayilov.techapp.service.impl;

import com.ismayilov.techapp.dto.request.UserRequestDTO;
import com.ismayilov.techapp.dto.response.CommonResponseDTO;
import com.ismayilov.techapp.dto.response.Status;
import com.ismayilov.techapp.dto.response.StatusCode;
import com.ismayilov.techapp.dto.response.UserResponseDTO;
import com.ismayilov.techapp.entity.TechUser;
import com.ismayilov.techapp.exception.UserAlreadyExist;
import com.ismayilov.techapp.repository.inter.UserRepository;
import com.ismayilov.techapp.service.inter.UserService;
import com.ismayilov.techapp.util.DTOUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserServiceImpl implements UserService {

    @Autowired
    DTOUtil dtoUtil;

    @Autowired
    UserRepository userRepository;

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
                .password(userRequestDTO.getPassword())
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
}
