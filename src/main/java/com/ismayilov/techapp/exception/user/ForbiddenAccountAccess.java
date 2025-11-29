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
public class ForbiddenAccountAccess extends RuntimeException {
    CommonResponseDTO<?> responseDTO;
}
