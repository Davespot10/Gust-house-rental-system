package com.example.mppproject.Service;

import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Enum.ApprovedStatus;
import com.example.mppproject.Model.Enum.ReservationStatusEnum;
import com.example.mppproject.Model.Payment;
import com.example.mppproject.Model.Property;
import com.example.mppproject.Model.Reservation;
import com.example.mppproject.Repository.*;
import com.example.mppproject.exceptionResponse.propertyException.PropertyNotFoundException;
import com.example.mppproject.exceptionResponse.reservationException.*;
import com.example.mppproject.exceptionResponse.userException.UserNotFoundException;
import com.example.mppproject.utility.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PropertyRepository propertyRepository;
    private final AppUserRepository appUserRepository;
    private final EmailSenderService emailSenderService;
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ReservationService(
            ReservationRepository reservationRepository,
            PropertyRepository propertyRepository,
            AppUserRepository appUserRepository,
            EmailSenderService emailSenderService,
            PaymentRepository paymentRepository,
            AccountRepository accountRepository
    ){
        this.reservationRepository = reservationRepository;
        this.propertyRepository = propertyRepository;
        this.appUserRepository = appUserRepository;
        this.emailSenderService = emailSenderService;
        this.paymentRepository = paymentRepository;
        this.accountRepository=accountRepository;
    }
    public Reservation createReservation(Long appUserId, Long propertyId, Reservation reservation) {


        Property property = propertyRepository.findById(propertyId).stream().findFirst().orElse(null);


        if(property == null)
            throw new PropertyNotFoundException("Property not found");

        AppUser appUser = appUserRepository.findById(appUserId).stream().findFirst().orElse(null);

        if(appUser == null)
            throw new UserNotFoundException("User not found");



        if(!property.getAvailabiltyStatus())
            throw new PropertyAlreadyReservedException("The property is already reserved");

        if(!property.getApprovedStatus().equals(ApprovedStatus.APPROVED))
            throw new PropertyNotApprovedException("The property is not approved by admin for reservation");

        LocalDate sDate = LocalDate.parse(reservation.getStartDate());
        LocalDate eDate = LocalDate.parse(reservation.getEndDate());


        if(sDate.isAfter(eDate) || sDate.isBefore(LocalDate.now()))
            throw new InvalidDateException("The Date Input is not valid for reservation");

        int numberOfDays = Period.between(sDate,eDate).getDays();

        System.out.println("number of days "+numberOfDays);
        if(sDate.equals(eDate)){
            numberOfDays = 1;
        }

        System.out.println("after if: "+numberOfDays);
        double calculatedPrice = calculatePrice(numberOfDays, property.getPricePerNight());

        String refNumber = generateRandomString(8);



        reservation.setCalculatedPrice(calculatedPrice);
        reservation.setAppUser(appUser);
        reservation.setProperty(property);
        reservation.setRefNumber(refNumber);
        reservation.setReservationStatus(ReservationStatusEnum.PENDING);

     return reservation;
    }
    public double calculatePrice(int numberOfDays, double pricePerNight){

        return numberOfDays*pricePerNight;

    }
    private String generateRandomString(int length) {

        String randomString = "";

        while (true) {
            if (randomString.length() >= length)
                return randomString.substring(0, length - 1);
            else
                randomString += java.util.UUID.randomUUID().toString().replaceAll("-", "");
        }
    }

    public List<Reservation> getReservations() {
        return  reservationRepository.findAll();
    }

    public Reservation getReservationByRef(String refNumber) {

       return reservationRepository.findReservationByRefNumber(refNumber).stream().findFirst().orElse(null);
    }

    public Reservation cancelReservation(String refNumber, Long appUserId) {
        Reservation reservation = reservationRepository.findReservationByRefNumber(refNumber).stream().findFirst().
                orElseThrow(()-> new ReservationNotFoundException("Reservation does not exist for cancel"));

        Payment payment = paymentRepository.findByReservation(reservation).stream().findFirst().
                orElseThrow(()-> new ReservationNotFoundException("Reservation does not exist for cancel"));

        if(!reservation.getReservationStatus().equals(ReservationStatusEnum.RESERVED))
            throw new ReservationNotFoundException("Reservation does not exist for cancel");

        reversePayment(payment, reservation, payment.getGuestAppUser(), payment.getHostAppUser(), reservation.getProperty());

        if(reservation.getReservationStatus().equals(ReservationStatusEnum.CANCELLED)){
            if(payment.getGuestAppUser().getFirstName()==null) payment.getGuestAppUser().setFirstName("");
            if(payment.getGuestAppUser().getLastName()==null) payment.getGuestAppUser().setLastName("");
            String emailTo = payment.getGuestAppUser().getUserName();
            String emailSubject = "DMZNeW Reservations - reservation conformation";
            String emailBody = "Dear " + payment.getGuestAppUser().getFirstName() + " " + payment.getGuestAppUser().getLastName() +",\n"+
                    "This is an automated email to conform that your reservation has been cancelled. Please do not reply to this email.\n"+

                    "Date: "+ LocalDate.now()+"\n";

            emailSenderService.sendEmail(emailTo,emailSubject,emailBody);
        }

        return reservation;
    }

    @Transactional
    public void reversePayment(Payment payment, Reservation reservation, AppUser guestAppUser, AppUser hostAppUser, Property property){

        double totalPayment = reservation.getCalculatedPrice();
        double guestBalance = guestAppUser.getAccount().getBalance();
        double hostBalance = hostAppUser.getAccount().getBalance();
        double deduction = 0.15*totalPayment;

        if(totalPayment > guestBalance){
            throw new InsufficientBalanceException("Insufficient balance to make payment");
        }

        guestBalance = guestBalance+totalPayment-deduction;
        guestAppUser.getAccount().setBalance(guestBalance);
        accountRepository.save(guestAppUser.getAccount());

        hostBalance = hostBalance - totalPayment+deduction;
        hostAppUser.getAccount().setBalance(hostBalance);
        accountRepository.save(hostAppUser.getAccount());

        reservation.setReservationStatus(ReservationStatusEnum.CANCELLED);
        reservationRepository.save(reservation);

        paymentRepository.save(payment);

        property.setAvailabiltyStatus(true);
        propertyRepository.save(property);





    }

    public List<Reservation> getReservationById(String id) throws Exception {
        try{
            Long userId = Long.valueOf(id);
            List<Reservation> reservation = reservationRepository.findReservationByAppUserId(userId);
            if(reservation.isEmpty()){
                throw new ReservationNotFoundException("Reservation not found");
            }
            return reservation;
        }catch (NumberFormatException e){
            throw new NumberFormatException();
        }
    }
}
