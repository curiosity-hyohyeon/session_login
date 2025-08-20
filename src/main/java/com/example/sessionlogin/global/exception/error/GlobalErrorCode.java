package com.example.sessionlogin.global.exception.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GlobalErrorCode implements ErrorCode{
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 입니다."),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
