package com.user.lms.entity;

import com.user.lms.models.UserDetailsModel;
import com.user.lms.models.UserModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_destination")
    private String startDestination;

    @Column(name = "end_destination")
    private String endDestination;

    @Column(name = "km")
    private int km;

    @Column(name = "is_Labourer")
    private Boolean isLabourer;

    @Column(name = "no_of_labourers")
    private int noOfLabourers;

    @Column(name = "fuel_charge")
    private int fuelCharge;

    @Column(name = "toll_charge")
    private int tollCharge;

    @Column(name = "labourer_charge")
    private int labourerCharge;

    @Column(name = "total_amount")
    private int totalAmount;

    @ManyToOne
    @JoinColumn(name = "truck_provider_id")
    private User driver;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private VehicleList vehicleList;

    @Column(name = "decline_reason")
    private String declineReason;

    @Column(name = "is_tp_approved")
    private Boolean isTPApproved;

    @Column(name = "is_cust_approved")
    private Boolean isCustApproved;

    @Column(name = "booking_date")
    private Date bookingDate;
}
