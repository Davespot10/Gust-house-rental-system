package com.example.mppproject.exceptionResponse.userException;

public class UserBadRequestException extends RuntimeException{
    public UserBadRequestException(String message) {
        super(message);
    }

}
