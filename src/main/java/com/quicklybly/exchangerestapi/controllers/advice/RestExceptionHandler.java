package com.quicklybly.exchangerestapi.controllers.advice;

import com.quicklybly.exchangerestapi.dto.ErrorDTO;
import com.quicklybly.exchangerestapi.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<ErrorDTO> handleAppException(AppException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(new ErrorDTO(exception.getMessage()));
    }
}
