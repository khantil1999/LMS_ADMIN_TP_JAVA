package com.user.lms.models;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateWiseBookingModel {
    private Date bookingDate;
    private long count;
}
