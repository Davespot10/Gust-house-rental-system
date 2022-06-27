package com.example.mppproject.Model;

import com.example.mppproject.Model.Enum.ApprovedStatus;
import com.example.mppproject.Model.Enum.Space;
import com.example.mppproject.Model.Enum.Type;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

// TODO: Home propertiy is not inserted


@Entity
@Table(name = "property")
public class Property {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "home_property_id")
    private HomeProperty homeProperty;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Property(){}

    public Property(Boolean availabiltyStatus,Double pricePerNight){
        this.availabiltyStatus=availabiltyStatus;
        this.pricePerNight=pricePerNight;
    }
    public Property(String title, Type type, Space space, String description, Address address, Double pricePerNight, ApprovedStatus approvedStatus, Boolean availabiltyStatus, Integer capacity, Set<Reservation> reservations, HomeProperty homeProperty, Set<Review> reviews) {
        this.title = title;
        this.type = type;
        this.space = space;
        this.description = description;
        this.address = address;
        this.pricePerNight = pricePerNight;
        this.approvedStatus = approvedStatus;
        this.availabiltyStatus = availabiltyStatus;
        this.capacity = capacity;
        this.homeProperty = homeProperty;
    }

    public HomeProperty getHomeProperty() {
        return homeProperty;
    }

    public void setHomeProperty(HomeProperty homeProperty) {
        this.homeProperty = homeProperty;
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
        Property property = (Property) o;
        return id != null && Objects.equals(id, property.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}