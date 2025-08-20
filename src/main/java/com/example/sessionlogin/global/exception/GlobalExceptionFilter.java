package com.example.sessionlogin.global.exception;

import com.example.sessionlogin.global.exception.error.ErrorCode;
import com.example.sessionlogin.global.exception.error.GlobalErrorCode;
import com.example.sessionlogin.global.exception.model.CustomException;
import com.example.sessionlogin.global.exception.model.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (CustomException e){
            ErrorCode errorCode = e.getErrorCode();
            ErrorResponse errorResponse = ErrorResponse.of(errorCode);
            log.error("CustomException 발생", e);
            errorToJson(errorCode, errorResponse, response);
        } catch (Exception e){
            ErrorCode errorCode = GlobalErrorCode.INTERNAL_SERVER_ERROR;
            ErrorResponse errorResponse = ErrorResponse.of(errorCode);
            log.error("예상치 못한 서버 에러 발생", e);
            errorToJson(errorCode, errorResponse, response);
        }
    }

    private void errorToJson(ErrorCode errorCode, ErrorResponse errorResponse, HttpServletResponse response) throws IOException {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
