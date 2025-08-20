package com.example.sessionlogin.global.exception.model;

import com.example.sessionlogin.global.exception.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public abstract class CustomException extends RuntimeException{
    private final ErrorCode errorCode;
    private final HttpStatus httpStatus;
    private final String message;
    private final LocalDateTime timestamp;

    protected CustomException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    protected CustomException(ErrorCode errorCode, Throwable cause){
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
        this.timestamp = LocalDateTime.now();
    }

}
