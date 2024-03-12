package com.user.lms.models;

import com.user.lms.entity.VehicleList;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
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

    public static VehicleDetailsModel fromEntity(VehicleList vehicleList) {
        VehicleDetailsModel vehicleDetailsModel = new VehicleDetailsModel();
        vehicleDetailsModel.setId(vehicleList.getId());
        vehicleDetailsModel.setModel(vehicleList.getModel());
        vehicleDetailsModel.setLicensePlate(vehicleList.getLicensePlate());
        vehicleDetailsModel.setCapacity(vehicleList.getCapacity());
        vehicleDetailsModel.setManufacturer(vehicleList.getManufacturer());
        vehicleDetailsModel.setFuelType(vehicleList.getFuelType());
        vehicleDetailsModel.setCurrentMileage(vehicleList.getCurrentMileage());
        vehicleDetailsModel.setWheel(vehicleList.getWheel());
        vehicleDetailsModel.setPhoto(vehicleList.getPhotos().stream()
                .map(PhotoModel::fromEntity)
                .collect(Collectors.toList()));
        vehicleDetailsModel.setTruckProvider(UserDetailsModel.fromEntity(vehicleList.getDriver()));
        return vehicleDetailsModel;
    }
}
