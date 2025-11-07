package com.ismayilov.techapp.util;

import com.ismayilov.techapp.dto.request.UserRequestDTO;
import com.ismayilov.techapp.dto.response.CommonResponseDTO;
import com.ismayilov.techapp.dto.response.Status;
import com.ismayilov.techapp.dto.response.StatusCode;
import com.ismayilov.techapp.exception.InvalidDTO;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class DTOUtil {

    @Autowired
    Logger logger;


    public void isValid(UserRequestDTO userRequestDTO) {
        checkDTOInput(userRequestDTO.getName());
        checkDTOInput(userRequestDTO.getSurname());
        checkDTOInput(userRequestDTO.getPin());
        checkDTOInput(userRequestDTO.getPassword());
        checkDTOInput(userRequestDTO.getAccountRequestDTOList());
    }

    private <T> void checkDTOInput(T t) {
        if (Objects.isNull(t) || t.toString().isBlank()) {
            logger.error("Invalid Input");
            throw InvalidDTO.builder().responseDTO(CommonResponseDTO.builder()
                    .status(Status.builder()
                            .statusCode(StatusCode.INVALID_DTO)
                            .message("Invalid data")
                            .build()
                    ).build()
            ).build();
        }
    }
}
