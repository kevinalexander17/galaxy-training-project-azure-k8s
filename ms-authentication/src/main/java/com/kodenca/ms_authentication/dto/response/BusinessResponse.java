package com.kodenca.ms_authentication.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BusinessResponse<T>{
    private boolean success;
    private String message;
    private LocalDateTime timestamp;
    private T data;
}
