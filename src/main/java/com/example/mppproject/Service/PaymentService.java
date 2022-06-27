package com.example.mppproject.Service;

import com.example.mppproject.Model.*;
import com.example.mppproject.Model.Enum.ReservationStatusEnum;
import com.example.mppproject.Repository.*;
import com.example.mppproject.exceptionResponse.propertyException.PropertyNotFoundException;
import com.example.mppproject.exceptionResponse.reservationException.ReservationCanceledByUserException;
import com.example.mppproject.exceptionResponse.reservationException.ReservationDateExpiredException;
import com.example.mppproject.exceptionResponse.reservationException.ReservationNotFoundException;
import com.example.mppproject.exceptionResponse.reservationException.ReservationPaymentIsMadeException;
import com.example.mppproject.exceptionResponse.userException.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final ReservationRepository reservationRepository;
    private final PropertyRepository propertyRepository;
    private final AppUserRepository appUserRepository;
    private final ReservationStatusRepository reservationStatusRepository;

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public PaymentService(
            ReservationRepository reservationRepository,
            PropertyRepository propertyRepository,
            AppUserRepository appUserRepository,
            PaymentRepository paymentRepository,
            ReservationStatusRepository reservationStatusRepository,
            AccountRepository accountRepository
    ){
        this.reservationRepository = reservationRepository;
        this.propertyRepository = propertyRepository;
        this.appUserRepository = appUserRepository;
        this.reservationStatusRepository = reservationStatusRepository;
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
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

        Property property = reservation.getProperty();
        if(property == null)
            throw new PropertyNotFoundException("Property not found");

        Payment payment = new Payment();
        payment.setAmount(reservation.getCalculatedPrice());
        payment.setReservation(reservation);
        payment.setGuestAppUser(reservation.getAppUser());
        payment.setHostAppUser(property.getAppUser());

        makePayment(payment);

        return payment;
    }

    @Transactional
    public void makePayment(Payment payment){
        Account guestAccount = payment.getGuestAppUser().getAccount();
        Account hostAccount = payment.getHostAppUser().getAccount();
        ReservationStatus resStat = payment.getReservation().getReservationStatus();

        if(payment.getAmount() < guestAccount.getBalance()){
            System.out.println("Error");
        }

        guestAccount.setBalance(guestAccount.getBalance() - payment.getAmount());
        hostAccount.setBalance(guestAccount.getBalance() - payment.getAmount());

        accountRepository.save(guestAccount);
        accountRepository.save(hostAccount);

        resStat.setStatus_name(String.valueOf(ReservationStatusEnum.RESERVED));

        reservationStatusRepository.save(resStat);

        paymentRepository.save(payment);

    }
}
