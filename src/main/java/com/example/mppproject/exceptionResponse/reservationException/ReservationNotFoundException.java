package com.example.mppproject.exceptionResponse.reservationException;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException(String message) {
        super(message);
    }
}
