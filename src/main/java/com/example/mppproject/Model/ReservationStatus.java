package com.example.mppproject.Model;

import com.example.mppproject.Model.Enum.RoleType;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "reservation_status")
public class ReservationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(name = "id", nullable = false)
    private Long id;


    @OneToMany(mappedBy = "reservationStatus", orphanRemoval = true, cascade=CascadeType.ALL)
    private Set<Reservation> reservations = new LinkedHashSet<>();

    @Column(name = "status_name", nullable = false, unique = true)
    private String statusName;

    public ReservationStatus(String stat) {
        statusName =stat;
    }

    public ReservationStatus() {

    }

    public String getStatus_name() {
        return statusName;
    }

    public void setStatus_name(String status_name) {
        this.statusName = status_name;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}