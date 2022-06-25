package com.example.mppproject.Service;

import com.example.mppproject.Model.Reservation;
import com.example.mppproject.Repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;

    }

    public void createReservation(Reservation reservation) {
        double price = calculatePrice(reservation);
//       if(userAccountBalance < price){
//
//       }

        reservation.setCalculatedPrice(price);
        reservationRepository.save(reservation);
    }
    public double calculatePrice(Reservation reservation){
//        int days = Period.between(reservation.getStartDate(),reservation.getEndDate()).getDays();
//        return days*reservation.getProperty().getPricePerNight();
            return 0.0;
    }

}
