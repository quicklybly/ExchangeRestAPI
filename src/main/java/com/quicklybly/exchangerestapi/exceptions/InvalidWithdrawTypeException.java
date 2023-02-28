package com.quicklybly.exchangerestapi.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidWithdrawTypeException extends AppException {
    public InvalidWithdrawTypeException() {
        super("Invalid withdraw type", HttpStatus.BAD_REQUEST);
    }
}
