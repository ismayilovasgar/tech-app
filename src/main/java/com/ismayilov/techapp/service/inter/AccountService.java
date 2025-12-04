package com.ismayilov.techapp.service.inter;

import com.ismayilov.techapp.dto.request.AccountToAccountRequestDTO;
import com.ismayilov.techapp.dto.response.CommonResponseDTO;

public interface AccountService {
    //    CommonResponseDTO<?> getActiveAccounts();
    //    CommonResponseDTO<?> getAccount();
    CommonResponseDTO<?> account2account(AccountToAccountRequestDTO dto);
}
