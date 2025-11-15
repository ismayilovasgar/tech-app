package com.ismayilov.techapp.dto.response;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommonResponseDTO<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    Status status;
    T data;
}
