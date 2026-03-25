package com.kodenca.ms_authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.kodenca.ms_authentication.util.Constants.USER_ALREADY_EXISTS;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {
    
    public UserAlreadyExistsException(String username) {
        super(USER_ALREADY_EXISTS + username);
    }
}
