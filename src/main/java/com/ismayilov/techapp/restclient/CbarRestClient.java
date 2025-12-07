package com.ismayilov.techapp.restclient;

import com.ismayilov.techapp.config.ApplicationConfig;
import com.ismayilov.techapp.dto.response.CommonResponseDTO;
import com.ismayilov.techapp.dto.response.Status;
import com.ismayilov.techapp.dto.response.StatusCode;
import com.ismayilov.techapp.dto.response.mbdto.ValCursResponseDTO;
import com.ismayilov.techapp.exception.cbar.CbarRestException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CbarRestClient {

    @Autowired
    RestTemplate restTemplate;

    public ValCursResponseDTO getCurrency() {
        ValCursResponseDTO valCursResponseDTO;
        try {
            valCursResponseDTO = restTemplate.getForObject(ApplicationConfig.urlMB, ValCursResponseDTO.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw CbarRestException.builder()
                    .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.CBAR_ERROR)
                            .message("Error happened while getting response from Cbar")
                            .build()).build()).build();
        }

        if (Objects.isNull(valCursResponseDTO)) {
            throw CbarRestException.builder()
                    .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.CBAR_ERROR)
                            .message("Error happened while getting response from Cbar")
                            .build()).build()).build();
        }
        return valCursResponseDTO;
    }

}
