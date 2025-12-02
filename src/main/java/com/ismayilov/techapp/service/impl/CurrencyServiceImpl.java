package com.ismayilov.techapp.service.impl;

import com.ismayilov.techapp.dto.response.CommonResponseDTO;
import com.ismayilov.techapp.dto.response.Status;
import com.ismayilov.techapp.dto.response.StatusCode;
import com.ismayilov.techapp.dto.response.mbdto.ValCursResponseDTO;
import com.ismayilov.techapp.restclient.CbarRestClient;
import com.ismayilov.techapp.service.inter.CurrencyService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CurrencyServiceImpl implements CurrencyService {

    @Autowired
    CbarRestClient cbarRestClient;

    public CommonResponseDTO<?> getCurrencyRate() {
        ValCursResponseDTO valCursResponseDTO = cbarRestClient.getCurrency();
        return CommonResponseDTO.builder()
                .status(Status.builder()
                        .statusCode(StatusCode.SUCCESS)
                        .message("All currencies").build())
                .data(valCursResponseDTO).build();
    }
}
