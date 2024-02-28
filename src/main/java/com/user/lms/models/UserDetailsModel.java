package com.user.lms.models;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsModel {

    private String firstName;

    private String lastName;

    private String mobileNo;

    private String email;

}
