package com.example.mppproject.exceptionResponse.reservationException;

public class InvalidDateException extends RuntimeException{
    public InvalidDateException(String message) {
        super(message);
    }


}