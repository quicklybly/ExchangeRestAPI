package com.quicklybly.exchangerestapi.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidExchangePairException extends AppException {
    public InvalidExchangePairException() {
        super("Invalid exchange pair", HttpStatus.BAD_REQUEST);
    }
}
