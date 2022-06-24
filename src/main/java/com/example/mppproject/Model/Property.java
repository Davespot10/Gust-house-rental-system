package com.example.mppproject.Model;

import com.example.mppproject.Model.Enum.ApprovedStatus;
import com.example.mppproject.Model.Enum.Space;
import com.example.mppproject.Model.Enum.Type;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

// TODO: Home propertiy is not inserted


@Entity
@Table(name = "property")
public class Property {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated
    @Column(name = "type", nullable = false)
    private Type type;

    @Enumerated
    @Column(name = "space", nullable = false)
    private Space space;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "price_per_night", nullable = false)
    private Double pricePerNight;

    @Enumerated
    @Column(name = "approved_status")
    private ApprovedStatus approvedStatus;

    @Column(name = "availabilty_status")
    private Boolean availabiltyStatus;

    @Column(name = "capacity")
    private Integer capacity;

    @OneToMany(mappedBy = "property", orphanRemoval = true)
    private Set<Reservation> reservations = new LinkedHashSet<>();

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "home_property_id")
    private HomeProperty homeProperty;

    @OneToMany(mappedBy = "property", orphanRemoval = true)
    private Set<Review> reviews = new LinkedHashSet<>();

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public HomeProperty getHomeProperty() {
        return homeProperty;
    }

    public void setHomeProperty(HomeProperty homeProperty) {
        this.homeProperty = homeProperty;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean getAvailabiltyStatus() {
        return availabiltyStatus;
    }

    public void setAvailabiltyStatus(Boolean availabiltyStatus) {
        this.availabiltyStatus = availabiltyStatus;
    }

    public ApprovedStatus getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(ApprovedStatus approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public Double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        this.space = space;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}