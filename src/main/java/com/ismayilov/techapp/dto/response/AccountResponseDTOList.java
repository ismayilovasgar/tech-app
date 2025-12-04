package com.ismayilov.techapp.dto.response;

import com.ismayilov.techapp.entity.Account;
import com.ismayilov.techapp.exception.global.NoActiveAccount;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponseDTOList implements Serializable {

    static final long serialVersionUID = 1L;

    List<AccountResponseDTO> accountResponseDTOList;

    public static AccountResponseDTOList entityToDTO(List<Account> accountList) {
//        accountList = accountList.stream().filter(Account::getIsActive).collect(Collectors.toList());
        accountList = new ArrayList<>(accountList);
        if (!accountList.isEmpty()) {
            List<AccountResponseDTO> accountResponseDTOList = new ArrayList<>();
            accountList.forEach(account -> accountResponseDTOList.add(AccountResponseDTO.fromEntity(account)));
            return AccountResponseDTOList.builder().accountResponseDTOList(accountResponseDTOList).build();
        } else {
            throw NoActiveAccount.builder()
                    .responseDTO(CommonResponseDTO.builder().status(Status.builder()
                            .statusCode(StatusCode.NOT_ACTIVE_ACCOUNT)
                            .message("There is no active account")
                            .build()).build()).build();

        }
    }
}
