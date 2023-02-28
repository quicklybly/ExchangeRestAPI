package com.quicklybly.exchangerestapi.exceptions.auth;

import com.quicklybly.exchangerestapi.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AppException {
    public UserNotFoundException() {
        super("User not found", HttpStatus.BAD_REQUEST);
    }
}
