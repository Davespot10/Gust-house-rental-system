package com.example.mppproject.Model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "home_property")
public class HomeProperty {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "bath_room_number")
    private Integer bathRoomNumber;

    @Column(name = "bed_number")
    private Integer bedNumber;

    @Column(name = "bed_room_number")
    private Integer bedRoomNumber;

    @Column(name = "description")
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBedRoomNumber() {
        return bedRoomNumber;
    }

    public void setBedRoomNumber(Integer bedRoomNumber) {
        this.bedRoomNumber = bedRoomNumber;
    }

    public Integer getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(Integer bedNumber) {
        this.bedNumber = bedNumber;
    }

    public Integer getBathRoomNumber() {
        return bathRoomNumber;
    }

    public void setBathRoomNumber(Integer bathRoomNumber) {
        this.bathRoomNumber = bathRoomNumber;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}