package com.example.mppproject.Controller;

import com.example.mppproject.Model.Account;
import com.example.mppproject.Model.Payment;
import com.example.mppproject.Model.Reservation;
import com.example.mppproject.Repository.ReservationRepository;
import com.example.mppproject.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    @Autowired
    public ReservationController(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservations(){
        List<Reservation> reservations = reservationService.getReservations();
        return new ResponseEntity<List<Reservation>>(reservations, HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/{refNum}")
    public ResponseEntity<Reservation> getResrvationByRef(@PathVariable("refNum") String refNum){
        Reservation reservation = reservationService.getReservationByRef(refNum);
        return new ResponseEntity<>(reservation, HttpStatus.ACCEPTED);
    }
    @PostMapping(path = "{appUserId}/{propertyId}")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation,
                                                         @PathVariable("appUserId") Long appUserId,
                                                         @PathVariable("propertyId") Long propertyId
    ){
        System.out.println(appUserId);
        System.out.println(propertyId);

       Reservation newReservation = reservationService.createReservation(appUserId, propertyId, reservation);
       return new ResponseEntity<Reservation>(newReservation, HttpStatus.ACCEPTED);
    }


}
