package com.ismayilov.techapp.service.impl;

import com.ismayilov.techapp.dto.request.AccountToAccountRequestDTO;
import com.ismayilov.techapp.dto.response.AccountResponseDTOList;
import com.ismayilov.techapp.dto.response.CommonResponseDTO;
import com.ismayilov.techapp.dto.response.Status;
import com.ismayilov.techapp.dto.response.StatusCode;
import com.ismayilov.techapp.entity.TechUser;
import com.ismayilov.techapp.exception.InvalidAmount;
import com.ismayilov.techapp.repository.inter.UserRepository;
import com.ismayilov.techapp.service.inter.AccountService;
import com.ismayilov.techapp.util.CurrentUser;
import com.ismayilov.techapp.util.DTOUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import java.util.Optional;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountServiceImpl implements AccountService {

    @Autowired
    CurrentUser currentUser;

    @Autowired
    DTOUtil dtoUtil;

    @Autowired
    UserRepository userRepository;

    public CommonResponseDTO<?> getAccount() {
        Optional<TechUser> user = userRepository.findByPin(currentUser.getCurrentUser().getUsername());

        return CommonResponseDTO.builder()
                .status(Status.builder()
                        .statusCode(StatusCode.SUCCESS)
                        .message("Accounts successfully fetched")
                        .build())
                .data(AccountResponseDTOList.entityToDTO(user.get().getAccountList()))
                .build();
    }

    @Override
    public CommonResponseDTO<?> getActiveAccounts() {
        Optional<TechUser> user = userRepository.findByPin(currentUser.getCurrentUser().getUsername());


        var activeAccounts = user.get().getAccountList()
                .stream()
                .filter(account -> Boolean.TRUE.equals(account.getIsActive()))
                .collect(Collectors.toList());

        return CommonResponseDTO.builder()
                .status(Status.builder()
                        .statusCode(StatusCode.SUCCESS)
                        .message("Active accounts successfully fetched")
                        .build())
                .data(AccountResponseDTOList.entityToDTO(activeAccounts))
                .build();
    }

    public CommonResponseDTO<?> account2account(AccountToAccountRequestDTO accountToAccountRequestDTO) {
        dtoUtil.isValid(accountToAccountRequestDTO);
        if (accountToAccountRequestDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw InvalidAmount.builder()
                    .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.INVALID_AMOUNT)
                            .message("Amount is not correct")
                            .build()).build()).build();
        }
        return null;
    }
}
