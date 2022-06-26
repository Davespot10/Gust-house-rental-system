package com.example.mppproject.Service;

import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Payment;
import com.example.mppproject.Model.Reservation;
import com.example.mppproject.Repository.*;
import com.example.mppproject.exceptionResponse.reservationException.ReservationCanceledByUserException;
import com.example.mppproject.exceptionResponse.reservationException.ReservationDateExpiredException;
import com.example.mppproject.exceptionResponse.reservationException.ReservationNotFoundException;
import com.example.mppproject.exceptionResponse.reservationException.ReservationPaymentIsMadeException;
import com.example.mppproject.exceptionResponse.userException.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final ReservationRepository reservationRepository;
    private final PropertyRepository propertyRepository;
    private final AppUserRepository appUserRepository;
    private final ReservationStatusRepository reservationStatusRepository;

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(
            ReservationRepository reservationRepository,
            PropertyRepository propertyRepository,
            AppUserRepository appUserRepository,
            PaymentRepository paymentRepository,
            ReservationStatusRepository reservationStatusRepository
    ){
        this.reservationRepository = reservationRepository;
        this.propertyRepository = propertyRepository;
        this.appUserRepository = appUserRepository;
        this.reservationStatusRepository = reservationStatusRepository;
        this.paymentRepository = paymentRepository;
    }
    public Payment createPayment(String refNumber, Long appUserId) {
        Reservation reservation = reservationRepository.findReservationByRefNumber(refNumber).stream().findFirst().orElse(null);
        if(reservation == null){
            throw new ReservationNotFoundException("Reservation not found with this reference number");
        }
//        LocalDate startDate = LocalDate.parse(reservation.getStartDate());
//        if(LocalDate.now().isAfter(startDate)){
//            throw new ReservationDateExpiredException("Reservation is expired // Payment date is after reservation start date");
//        }


        if(reservation.getReservationStatus().equals("EXPIRED")){
            throw new ReservationDateExpiredException("Reservation is expired // Payment date is after reservation start date");
        } else if (reservation.getReservationStatus().equals("CANCELLED")) {
            throw new ReservationCanceledByUserException("Reservation is canceled by user");
        } else if (reservation.getReservationStatus().equals("RESERVED")) {
            throw new ReservationPaymentIsMadeException("User already paid for the reservation");
        }

        AppUser appUser = appUserRepository.findById(appUserId).stream().findFirst().orElse(null);
        if(appUser == null)
            throw new UserNotFoundException("User not found");


        return null;
    }
}
