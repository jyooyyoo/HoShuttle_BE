package com.hoseobus.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse<?>> handleCustomException(CustomException ex) {
        return ResponseEntity.badRequest().body(BaseResponse.fail(ex.getErrorCode().name(), ex.getMessage()));
    }
}
