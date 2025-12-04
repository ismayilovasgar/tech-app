package com.ismayilov.techapp.util.factory;

import com.ismayilov.techapp.dto.response.CommonResponseDTO;
import com.ismayilov.techapp.dto.response.Status;
import com.ismayilov.techapp.dto.response.StatusCode;

public class ResponseFactory {

    public static CommonResponseDTO<?> error(StatusCode code, String message) {
        return CommonResponseDTO.builder()
                .status(Status.builder()
                        .statusCode(code)
                        .message(message)
                        .build())
                .build();
    }

    public static CommonResponseDTO<?> success(Object data, String message) {
        return CommonResponseDTO.builder()
                .status(Status.builder()
                        .statusCode(StatusCode.SUCCESS)
                        .message(message)
                        .build())
                .data(data)
                .build();
    }
}
