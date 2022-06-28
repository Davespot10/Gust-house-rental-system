package com.example.mppproject.exceptionResponse.reservationException;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String message){super(message);}
}
