package com.user.lms.models;

import com.user.lms.entity.VehicleList;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingModel {

    private Long id;
    private String startDestination;

    private String endDestination;

    private int km;

    private Boolean isLabourer;

    private int noOfLabourers;

    private int fuelCharge;

    private int tollCharge;

    private int labourerCharge;

    private int totalAmount;

    private UserDetailsModel user;
    private UserDetailsModel truckProvider;

    private VehicleListModel vehicles;

    private String declineReason;

    private Boolean isTPApproved;

    private Boolean isCustApproved;

    private Date bookingDate;
}
