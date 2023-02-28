package com.quicklybly.exchangerestapi.exceptions;

import org.springframework.http.HttpStatus;

public class UnsupportedCurrencyException extends AppException {
    public UnsupportedCurrencyException() {
        super("Unsupported currency", HttpStatus.BAD_REQUEST);
    }
}
