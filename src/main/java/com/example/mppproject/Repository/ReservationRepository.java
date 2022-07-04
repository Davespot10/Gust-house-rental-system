package com.example.mppproject.Repository;

import com.example.mppproject.Model.*;
import com.example.mppproject.Model.Enum.ReservationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findReservationByRefNumber(String refNumber);
    Optional <Reservation> findFirstByPropertyAndAppUser(Property property, AppUser appUser);
    List<Reservation> findReservationByAppUserIdAndReservationStatus(Long id, ReservationStatusEnum reservationStatusEnum);
}
