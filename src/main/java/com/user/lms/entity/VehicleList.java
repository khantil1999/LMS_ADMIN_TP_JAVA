package com.user.lms.entity;

import com.user.lms.models.VehicleAddEditRequestModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
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


    @OneToMany(mappedBy = "vehicle")
    private List<Photo> photos;

    @ManyToOne
    @JoinColumn(name = "truck_provider_id")
    private User driver;
    public static VehicleList fromModel(VehicleAddEditRequestModel data) {
        VehicleList vehicle = new VehicleList();
        vehicle.setModel(data.getModel());
        vehicle.setLicensePlate(data.getLicensePlate());
        vehicle.setCapacity(Integer.parseInt(data.getCapacity()));
        vehicle.setManufacturer(data.getManufacturer());
        vehicle.setFuelType(data.getFuelType());
        vehicle.setCurrentMileage(Integer.parseInt(data.getCurrentMileage()));
        vehicle.setWheel(data.getWheel());
        return vehicle;
    }

}
