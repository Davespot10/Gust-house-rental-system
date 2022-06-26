package com.example.mppproject.Controller;

import com.example.mppproject.Model.Account;
import com.example.mppproject.Model.Payment;
import com.example.mppproject.Model.Reservation;
import com.example.mppproject.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @PostMapping(path = "{appUserId}/{propertyId}")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation,
                                                         @PathVariable("appUserId") Long appUserId,
                                                         @PathVariable("propertyId") Long propertyId
    ){

       Reservation newReservation = reservationService.createReservation(appUserId, propertyId, reservation);
       return new ResponseEntity<Reservation>(newReservation, HttpStatus.ACCEPTED);
    }


}
