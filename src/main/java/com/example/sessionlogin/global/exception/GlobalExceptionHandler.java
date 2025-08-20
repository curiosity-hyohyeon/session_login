package com.example.sessionlogin.global.exception;

import com.example.sessionlogin.global.exception.error.ErrorCode;
import com.example.sessionlogin.global.exception.error.GlobalErrorCode;
import com.example.sessionlogin.global.exception.model.CustomException;
import com.example.sessionlogin.global.exception.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.validation.BindException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> customExceptionHandling(CustomException e){
        ErrorResponse errorResponse = ErrorResponse.of(e.getErrorCode());
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> bindExceptionHandling(BindException e){
        ErrorCode errorCode = GlobalErrorCode.BAD_REQUEST;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, toErrorMap(e.getBindingResult()));
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandling(Exception e){
        ErrorCode errorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
    }

    private Map<String, String> toErrorMap(BindingResult br){
        Map<String, String> errors = new LinkedHashMap<>();
        for(FieldError fieldError : br.getFieldErrors()){
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }
}
