package com.example.mppproject.exceptionResponse.reservationException;

public class ReservationCanceledByUserException extends RuntimeException{
   public ReservationCanceledByUserException(String message){ super(message);}
}
