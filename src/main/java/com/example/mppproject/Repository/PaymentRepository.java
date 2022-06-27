package com.example.mppproject.Repository;

import com.example.mppproject.Model.Payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
//    Optional<Payment> findReservationStatusByStatusName(String status_name);
}
