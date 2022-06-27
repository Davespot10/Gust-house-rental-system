package com.example.mppproject.Model.seed;

import com.example.mppproject.Model.Enum.ReservationStatusEnum;
import com.example.mppproject.Model.ReservationStatus;
import com.example.mppproject.Repository.ReservationStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ReservationStatusSeed implements CommandLineRunner {

    ReservationStatusRepository reservationStatusRepository;
    public ReservationStatusSeed(ReservationStatusRepository reservationStatusRepository){
        this.reservationStatusRepository = reservationStatusRepository;
    }
    @Override
    public void run(String... args) throws Exception {
                 loadData();
    }

    private void loadData() {
        if (reservationStatusRepository.count() == 0) {
            ReservationStatus data1 = new ReservationStatus(String.valueOf(ReservationStatusEnum.RESERVED));
            ReservationStatus data2 = new ReservationStatus(String.valueOf(ReservationStatusEnum.CANCELLED));
            ReservationStatus data3 = new ReservationStatus(String.valueOf(ReservationStatusEnum.EXPIRED));
            ReservationStatus data4 = new ReservationStatus(String.valueOf(ReservationStatusEnum.PENDING));
            reservationStatusRepository.save(data1);
            reservationStatusRepository.save(data2);
            reservationStatusRepository.save(data3);
            reservationStatusRepository.save(data4);
            }

    }
}
