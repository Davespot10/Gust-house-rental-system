package com.example.mppproject.Controller;

import com.example.mppproject.Model.Payment;
import com.example.mppproject.Model.Reservation;
import com.example.mppproject.Service.PaymentService;
import com.example.mppproject.Service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;
    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping (path = "/{refNumber}/{appUserId}")
    public ResponseEntity<Payment> payment(@RequestBody Reservation reservation, @PathVariable("refNumber") String refNumber, @PathVariable("appUserId") Long appUserId){

        Payment newPayment = paymentService.createPayment(refNumber, appUserId, reservation);
        return new ResponseEntity<Payment>(newPayment, HttpStatus.OK);

    }
}
