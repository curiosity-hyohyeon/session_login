package com.example.sessionlogin.global.exception.model;

import com.example.sessionlogin.global.exception.error.ErrorCode;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Builder
public record ErrorResponse(
        HttpStatus httpStatus,
        String message,
        LocalDateTime timestamp,
        Map<String, String> errors
) {
    public static ErrorResponse of(ErrorCode errorCode){
        return ErrorResponse.builder()
                .httpStatus(errorCode.getHttpStatus())
                .message(errorCode.getMessage())
                .timestamp(LocalDateTime.now())
                .errors(new LinkedHashMap<>())
                .build();
    }


    public static ErrorResponse of(ErrorCode errorCode, Map<String, String> errors){
        return ErrorResponse.builder()
                .httpStatus(errorCode.getHttpStatus())
                .message(errorCode.getMessage())
                .timestamp(LocalDateTime.now())
                .errors(errors)
                .build();
    }
}
