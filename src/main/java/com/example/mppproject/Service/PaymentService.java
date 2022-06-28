package com.example.mppproject.Service;

import com.example.mppproject.Model.*;
import com.example.mppproject.Model.Enum.ReservationStatusEnum;
import com.example.mppproject.Repository.*;
import com.example.mppproject.exceptionResponse.propertyException.PropertyNotFoundException;
import com.example.mppproject.exceptionResponse.reservationException.*;
import com.example.mppproject.exceptionResponse.userException.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    private final ReservationRepository reservationRepository;
    private final PropertyRepository propertyRepository;
    private final AppUserRepository appUserRepository;

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public PaymentService(
            ReservationRepository reservationRepository,
            PropertyRepository propertyRepository,
            AppUserRepository appUserRepository,
            PaymentRepository paymentRepository,
            AccountRepository accountRepository
    ){
        this.reservationRepository = reservationRepository;
        this.propertyRepository = propertyRepository;
        this.appUserRepository = appUserRepository;
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
    }
    public Payment createPayment(String refNumber, Long appUserId) {
        Reservation reservation = reservationRepository.findReservationByRefNumber(refNumber).stream().findFirst().orElse(null);
        if(reservation == null){
            throw new ReservationNotFoundException("Reservation not found with this reference number");
        }
        LocalDate startDate = LocalDate.parse(reservation.getStartDate());
        if(LocalDate.now().isAfter(startDate)){
            throw new ReservationDateExpiredException("Reservation is expired // Payment date is after reservation start date");
        }

        if(reservation.getReservationStatus().equals(ReservationStatusEnum.EXPIRED)){
            throw new ReservationDateExpiredException("Reservation is expired // Payment date is after reservation start date");
        } else if (reservation.getReservationStatus().equals(ReservationStatusEnum.CANCELLED)) {
            throw new ReservationCanceledByUserException("Reservation is canceled by user");
        } else if (reservation.getReservationStatus().equals(ReservationStatusEnum.RESERVED)) {
            throw new ReservationPaymentIsMadeException("User already paid for the reservation");
        }

        AppUser guestAppUser = appUserRepository.findById(appUserId).stream().findFirst().orElse(null);
        if(guestAppUser == null)
            throw new UserNotFoundException("User not found");

        Property property = reservation.getProperty();

        AppUser hostAppUser = property.getAppUser();
        Payment payment = new Payment();
        payment.setAmount(reservation.getCalculatedPrice());
        payment.setReservation(reservation);
        payment.setGuestAppUser(guestAppUser);
        payment.setHostAppUser(hostAppUser);

        makePayment(payment, reservation, guestAppUser, hostAppUser);

        return payment;
    }

    @Transactional
    public void makePayment(Payment payment, Reservation reservation, AppUser guestAppUser, AppUser hostAppUser){

        double totalPayment = reservation.getCalculatedPrice();
        double guestBalance = guestAppUser.getAccount().getBalance();
        double hostBalance = hostAppUser.getAccount().getBalance();

        if(totalPayment > guestBalance){
            throw new InsufficientBalanceException("Insufficient balance to make payment");
        }

        guestBalance = guestBalance-totalPayment;
        guestAppUser.getAccount().setBalance(guestBalance);
        accountRepository.save(guestAppUser.getAccount());

        hostBalance = hostBalance + totalPayment;
        hostAppUser.getAccount().setBalance(hostBalance);
        accountRepository.save(hostAppUser.getAccount());

        reservation.setReservationStatus(ReservationStatusEnum.RESERVED);
        reservationRepository.save(reservation);

        paymentRepository.save(payment);

    }
}
