package com.user.lms.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;

import java.util.List;

@ToString
@Getter
@Setter
@Entity
@Table(name = "Vehicle_Details")

public class VehicleList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private String licensePlate;
    private int capacity;
    private String manufacturer;
    private String fuelType;
    private int currentMileage;
    private String wheel;

    @ManyToOne
    @JoinColumn(name = "truck_provider_id")
    private User driver;


}
