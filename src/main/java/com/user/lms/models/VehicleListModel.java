package com.user.lms.models;

import com.user.lms.entity.Photo;
import com.user.lms.entity.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VehicleListModel {

    private Long id;

    private String model;


    private String licensePlate;

    private int capacity;


    private String manufacturer;

    private String fuelType;


    private int currentMileage;

    private String wheel;

    private MultipartFile[] photo;



}
