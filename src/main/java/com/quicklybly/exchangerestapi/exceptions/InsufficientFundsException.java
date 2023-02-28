package com.quicklybly.exchangerestapi.exceptions;

import org.springframework.http.HttpStatus;

public class InsufficientFundsException extends AppException {
    public InsufficientFundsException() {
        super("The account had insufficient funds", HttpStatus.BAD_REQUEST);
    }
}
