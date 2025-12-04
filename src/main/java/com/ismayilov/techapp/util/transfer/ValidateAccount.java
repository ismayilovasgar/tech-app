package com.ismayilov.techapp.util.transfer;

import com.ismayilov.techapp.dto.request.AccountToAccountRequestDTO;
import com.ismayilov.techapp.dto.response.CommonResponseDTO;
import com.ismayilov.techapp.dto.response.Status;
import com.ismayilov.techapp.dto.response.StatusCode;
import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.entity.TechUser;
import com.ismayilov.techapp.exception.global.InvalidToken;
import com.ismayilov.techapp.repository.inter.UserRepository;
import com.ismayilov.techapp.util.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ValidateAccount {

    @Autowired
    CurrentUser currentUser;

    @Autowired
    UserRepository userRepository;

    public void validateUserAndAccount(AccountToAccountRequestDTO requestDTO) {
        Optional<TechUser> user = userRepository.findByPin(currentUser.getCurrentUser().getUsername());

        if (user.isEmpty()) {
            throw InvalidToken.builder()
                    .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.INVALID_TOKEN)
                            .message("The token is not tied to this user")
                            .build()).build()).build();
        }

        TechUser techUser = user.get();
        List<Account> accountList = techUser.getAccountList();
        if (accountList.stream().noneMatch(account -> account.getAccountNo().equals(requestDTO.getDebitAccount()))
        ) {
            throw InvalidToken.builder()
                    .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.FORBIDDEN_ACCOUNT_ACCESS)
                            .message("The token is not tied to this user")
                            .build()).build()).build();
        }
    }

}
