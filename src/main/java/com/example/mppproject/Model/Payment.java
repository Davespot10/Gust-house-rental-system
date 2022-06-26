package com.example.mppproject.Model;

import javax.persistence.*;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "guest_app_user_id", nullable = false)
    private AppUser guestAppUser;

    @ManyToOne
    @JoinColumn(name = "host_app_user_id")
    private AppUser hostAppUser;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public AppUser getHostAppUser() {
        return hostAppUser;
    }

    public void setHostAppUser(AppUser hostAppUser) {
        this.hostAppUser = hostAppUser;
    }

    public AppUser getGuestAppUser() {
        return guestAppUser;
    }

    public void setGuestAppUser(AppUser guestAppUser) {
        this.guestAppUser = guestAppUser;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}