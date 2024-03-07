package com.user.lms.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingDeclineModel {

    private String reason;
    private Boolean isTPApproved;

}
