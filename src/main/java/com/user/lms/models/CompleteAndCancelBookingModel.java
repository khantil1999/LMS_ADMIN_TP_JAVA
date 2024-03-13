package com.user.lms.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompleteAndCancelBookingModel {
    private long additionalCharges;
    private String additionalChargesDetails;

    private String closeBookingReason;
}
