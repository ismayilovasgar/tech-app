package com.ismayilov.techapp.service.impl;

import com.ismayilov.techapp.dto.request.AccountToAccountRequestDTO;
import com.ismayilov.techapp.dto.response.*;
import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.entity.TechUser;
import com.ismayilov.techapp.repository.inter.UserRepository;
import com.ismayilov.techapp.util.transfer.*;
import com.ismayilov.techapp.service.inter.AccountService;
import com.ismayilov.techapp.util.security.CurrentUser;
import com.ismayilov.techapp.util.factory.ResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
//@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final TransferValidator transferValidator;
    private final AccountResolver accountResolver;
    private final TransferRuleChecker ruleChecker;
    private final TransferProcessor processor;

    @Autowired
    CurrentUser currentUser;

    @Autowired
    ValidateAccount validateAccount;


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


    @Transactional
    @Override
    public CommonResponseDTO<?> account2account(AccountToAccountRequestDTO dto) {
        validateAccount.validateUserAndAccount(dto);
        transferValidator.validate(dto);

        Account debit = accountResolver.getDebitAccount((dto.getDebitAccount()));
        Account credit = accountResolver.getCreditAccount(dto.getCreditAccount());

        ruleChecker.checkDebitAccount(debit, dto.getAmount());
        // ruleChecker.checkCreditAccount(credit, dto.getAmount());

        processor.process(debit, credit, dto.getAmount());

        return ResponseFactory.success(
                AccountResponseDTO.fromEntity(debit),
                "Transfer completed successfully"
        );
    }
}

