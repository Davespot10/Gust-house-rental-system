package com.example.mppproject.Repository;
import java.util.Optional;

import com.example.mppproject.Model.ReservationStatus;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationStatusRepository extends JpaRepository<ReservationStatus, Long> {


Optional<ReservationStatus> findReservationStatusByStatusName(String StatusName);
}
