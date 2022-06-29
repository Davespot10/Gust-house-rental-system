package com.example.mppproject.Controller;

import com.example.mppproject.Model.Account;
import com.example.mppproject.Model.Payment;
import com.example.mppproject.Model.Reservation;
import com.example.mppproject.Model.authentication.MyUserDetails;
import com.example.mppproject.Repository.ReservationRepository;
import com.example.mppproject.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/reservation")
public class ReservationController {
//    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    String username = ((UserDetails)principal).getUsername();

    private final ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }
//reservation for properties
    @GetMapping //admin
    public ResponseEntity<List<Reservation>> getReservations(){
        List<Reservation> reservations = reservationService.getReservations();
        return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/{referenceNumber}") //admin and guest
    public ResponseEntity<Reservation> getResrvationByRef(@PathVariable("referenceNumber") String referenceNumber){
        Reservation reservation = reservationService.getReservationByRef(referenceNumber);
        return new ResponseEntity<>(reservation, HttpStatus.ACCEPTED);
    }
    @PostMapping(path = "{appUserId}/{propertyId}") //guest
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation,
                                                         @PathVariable("appUserId") Long appUserId,
                                                         @PathVariable("propertyId") Long propertyId
    ){

        Reservation newReservation = reservationService.createReservation(appUserId, propertyId, reservation);
       return new ResponseEntity<Reservation>(newReservation, HttpStatus.ACCEPTED);
    }

    @PutMapping(path = "{refNumber}")
    public ResponseEntity<Reservation> cancelReservation(@RequestBody Reservation reservation,
                                                         @PathVariable("refNumber") Long refNumber
    ){
        Reservation canceledReservation = reservationService.cancelReservation(refNumber);
        return new ResponseEntity<Reservation>(canceledReservation, HttpStatus.ACCEPTED);
    }

}
