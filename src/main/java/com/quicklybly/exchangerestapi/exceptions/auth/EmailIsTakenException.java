package com.quicklybly.exchangerestapi.exceptions.auth;

import com.quicklybly.exchangerestapi.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class EmailIsTakenException extends AppException {
    public EmailIsTakenException() {
        super("Email is taken", HttpStatus.CONFLICT);
    }
}
