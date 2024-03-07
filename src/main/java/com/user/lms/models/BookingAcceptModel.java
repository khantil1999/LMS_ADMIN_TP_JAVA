package com.user.lms.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingAcceptModel {

    private int fuelCharge;
    private int tollCharge;
    private int labourerCharge;
    private int totalAmount;
    private Boolean isTPApproved;
}
