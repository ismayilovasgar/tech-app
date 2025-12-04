package com.ismayilov.techapp.service.impl.transfer;


import com.ismayilov.techapp.dto.request.AccountToAccountRequestDTO;
import com.ismayilov.techapp.entity.TechUser;
import com.ismayilov.techapp.repository.inter.UserRepository;
import com.ismayilov.techapp.util.general.AccountDTOUtil;
import com.ismayilov.techapp.util.security.CurrentUser;
import com.ismayilov.techapp.util.general.DTOUtil;
import com.ismayilov.techapp.util.factory.ExceptionFactory;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferValidator {

    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    private final DTOUtil dtoUtil;
    private final AccountDTOUtil accountDTOUtil;

    public void validate(AccountToAccountRequestDTO dto) {

        dtoUtil.isValid(dto);
        accountDTOUtil.checkInvalidAmount(dto);
        accountDTOUtil.checkAccountNo(dto);

        TechUser user = userRepository.findByPin(currentUser.getCurrentUser().getUsername())
                .orElseThrow(ExceptionFactory::invalidToken);

        boolean hasAccount = user.getAccountList().stream()
                .anyMatch(a -> a.getAccountNo().equals(dto.getDebitAccount()));

        if (!hasAccount) {
            throw ExceptionFactory.forbiddenAccountAccess();
        }
    }
}


