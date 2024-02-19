package com.user.lms.models;

import com.user.lms.entity.Photo;
import com.user.lms.entity.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VehicleListModel {

    private Long id;

    @NotEmpty(message = "Model cannot be blank")
    private String model;

    @NotEmpty(message = "License Plate cannot be blank")
    @Pattern(regexp = "^(?i)GJ\\d{2}[A-Za-z]{2}\\d{4}$",
            message = "License Plate should be in the proper format")
    @Size(min = 10, max = 10, message = "License Plate should be 10 characters long")
    @Column(unique = true, nullable = false)
    private String licensePlate;

    @NotNull(message = "Capacity cannot be blank")
    @Min(value = 1,message = "capacity must be greater than 0")
    private int capacity;

    @NotEmpty(message = "Manufacturer cannot be blank")
    private String manufacturer;

    @NotEmpty(message = "Fuel Type cannot be blank")
    private String fuelType;

    @NotNull(message = "Current Mileage cannot be blank")
    @Min(value = 1,message = "currentMileage must be greater than 0")
    private int currentMileage;

    @NotEmpty(message = "wheel cannot be blank")
    private String wheel;

    @NotNull(message = "At least one photo required")
    private Photo photo;
    private User driver;


}
