package com.ismayilov.techapp.exception;


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
public class InvalidDTO extends RuntimeException {

    CommonResponseDTO<?> responseDTO;
}
