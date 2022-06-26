package com.example.mppproject.exceptionResponse.reservationException;

public  class PropertyAlreadyReservedException extends RuntimeException{
    public PropertyAlreadyReservedException(String message) {super(message);}

}