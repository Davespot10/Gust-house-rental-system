package com.example.mppproject.Service;

import com.example.mppproject.Model.AppUser;
import com.example.mppproject.Model.Enum.ReservationStatusEnum;
import com.example.mppproject.Model.Property;
import com.example.mppproject.Model.Reservation;
import com.example.mppproject.Repository.AppUserRepository;
import com.example.mppproject.Repository.PropertyRepository;
import com.example.mppproject.Repository.ReservationRepository;
import com.example.mppproject.exceptionResponse.propertyException.PropertyNotFoundException;
import com.example.mppproject.exceptionResponse.reservationException.InvalidDateException;
import com.example.mppproject.exceptionResponse.reservationException.PropertyAlreadyReservedException;
import com.example.mppproject.exceptionResponse.userException.UserNotFoundException;
import com.example.mppproject.utility.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PropertyRepository propertyRepository;
    private final AppUserRepository appUserRepository;
    private final EmailSenderService emailSenderService;

    @Autowired
    public ReservationService(
            ReservationRepository reservationRepository,
            PropertyRepository propertyRepository,
            AppUserRepository appUserRepository,
            EmailSenderService emailSenderService
    ){
        this.reservationRepository = reservationRepository;
        this.propertyRepository = propertyRepository;
        this.appUserRepository = appUserRepository;
        this.emailSenderService = emailSenderService;
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

        LocalDate sDate = LocalDate.parse(reservation.getStartDate());
        LocalDate eDate = LocalDate.parse(reservation.getEndDate());

        if(sDate.isAfter(eDate) || sDate.isBefore(LocalDate.now()))
            throw new InvalidDateException("The Date Input is not valid for reservation");

        int numberOfDays = Period.between(sDate,eDate).getDays();
        double calculatedPrice = calculatePrice(numberOfDays, property.getPricePerNight());

        String refNumber = generateRandomString(8);



        reservation.setCalculatedPrice(calculatedPrice);
        reservation.setAppUser(appUser);
        reservation.setProperty(property);
        reservation.setRefNumber(refNumber);
        reservation.setReservationStatus(ReservationStatusEnum.PENDING);

        reservationRepository.save(reservation);
        property.setAvailabiltyStatus(false);
        propertyRepository.save(property);

        String emailTo = appUser.getUserName();
        String emailSubject = "DMZNeW Reservations - reservation conformation";
        String emailBody = "Dear " + appUser.getFirstName() + " " + appUser.getLastName() +",\n"+
                "This is an automated email that confirms your reservation. Please do not reply to this email.\n"+
                "Confirmation number: " +reservation.getRefNumber()+"\n"+
                "Date: "+ LocalDate.now().toString() +"\n";



        emailSenderService.sendEmail(emailTo, emailSubject, emailBody);

        return reservation;
    }
    private double calculatePrice(int numberOfDays, double pricePerNight){

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

    public Reservation cancelReservation(Long refNumber) {

        return null;
    }
}
