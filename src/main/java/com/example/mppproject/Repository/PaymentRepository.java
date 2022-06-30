package com.example.mppproject.Repository;

import com.example.mppproject.Model.Payment;

import com.example.mppproject.Model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    public Optional<Payment> findByReservation(Reservation reservation);

}
