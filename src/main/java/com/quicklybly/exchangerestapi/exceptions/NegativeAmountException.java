package com.quicklybly.exchangerestapi.exceptions;

import org.springframework.http.HttpStatus;

public class NegativeAmountException extends AppException {
    public NegativeAmountException() {
        super("Negative amount not allowed", HttpStatus.BAD_REQUEST);
    }
}
