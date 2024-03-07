package com.user.lms.expections;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookingNotFoundException extends RuntimeException{

    String message;

}
