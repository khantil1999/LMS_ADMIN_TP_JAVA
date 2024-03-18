package com.user.lms.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class VehicleAddEditRequestModel {
    private String capacity;
    private String currentMileage;
    private String fuelType;
    private String licensePlate;
    private String manufacturer;
    private String model;
    private List<String> photos;
    private String wheel;

}
