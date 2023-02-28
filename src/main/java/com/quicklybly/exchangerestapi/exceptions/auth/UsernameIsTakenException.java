package com.quicklybly.exchangerestapi.exceptions.auth;

import com.quicklybly.exchangerestapi.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class UsernameIsTakenException extends AppException {
    public UsernameIsTakenException() {
        super("Username is taken", HttpStatus.CONFLICT);
    }
}
