package com.example.mppproject.Model;

import com.example.mppproject.Model.Enum.ReservationStatusEnum;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "calculated_price")
    private Double calculatedPrice;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "end_date")
    private String endDate;

    @ManyToOne()
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne()
    @JoinColumn(name = "property_id")
    private Property property;

    @Column(name = "ref_number", nullable = false, unique = true)
    private String refNumber;

    @Column(name = "reservation_status")
    private ReservationStatusEnum reservationStatus;

    public Reservation(Double calculatedPrice,
                       String startDate,
                       String endDate,
                       AppUser appUser,
                       Property property,
                       String refNumber,
                       ReservationStatusEnum reservationStatus) {
        this.calculatedPrice = calculatedPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.appUser = appUser;
        this.property = property;
        this.refNumber = refNumber;
        this.reservationStatus = reservationStatus;
    }

    public Reservation() {

    }

    public ReservationStatusEnum getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatusEnum reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public Property getProperty() {
        return property;
    }


    public void setProperty(Property property) {
        this.property = property;
    }

    public AppUser getAppUser() {
        return appUser;
    }


    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Double getCalculatedPrice() {
        return calculatedPrice;
    }

    public void setCalculatedPrice(Double calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Reservation that = (Reservation) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}