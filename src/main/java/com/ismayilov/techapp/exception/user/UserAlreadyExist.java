package com.ismayilov.techapp.exception.user;


import com.ismayilov.techapp.dto.response.CommonResponseDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAlreadyExist extends RuntimeException {
    private final CommonResponseDTO<?> responseDTO;

    public UserAlreadyExist(CommonResponseDTO<?> responseDTO) {
        super(responseDTO.getStatus().getMessage());
        this.responseDTO = responseDTO;
    }

    public CommonResponseDTO<?> getResponseDTO() {
        return responseDTO;
    }
}
