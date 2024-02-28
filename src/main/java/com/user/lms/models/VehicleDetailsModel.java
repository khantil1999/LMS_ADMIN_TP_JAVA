package com.user.lms.models;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VehicleDetailsModel {

    private Long id;

    private String model;


    private String licensePlate;

    private int capacity;


    private String manufacturer;

    private String fuelType;


    private int currentMileage;

    private String wheel;

     private List<PhotoModel> photo;

     private UserDetailsModel truckProvider;
}
