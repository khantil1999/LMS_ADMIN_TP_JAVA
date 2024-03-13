package com.user.lms.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity()
@Table(name = "booking")
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

    @Column(name = "goods_type")
    private String goodsType;

    @Column(name = "booking_confirm_payment_image_path")
    private String bookingConfirmPaymentImagePath;

    @Column(name = "is_partial_payment_received")
    private Boolean isPartialPaymentReceived = false;

    @Column(name = "is_full_payment_received")
    private Boolean isFullPaymentReceived = false;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(name = "additional_charges")
    private Long additionalCharges ;

    @Column(name = "additional_charges_reason")
    private String additionalChargesReason;

}
