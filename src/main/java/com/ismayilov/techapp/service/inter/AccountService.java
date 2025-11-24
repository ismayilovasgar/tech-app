package com.ismayilov.techapp.service.inter;

import com.ismayilov.techapp.dto.response.CommonResponseDTO;

public interface AccountService {
    CommonResponseDTO<?> getActiveAccounts();
    CommonResponseDTO<?> getAccount();
}
