package com.user.lms.models;

import com.user.lms.entity.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDetailsModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNo;
    private String qrCodePath;

    public static UserDetailsModel fromEntity(User user) {
        UserDetailsModel userDetailsModel = new UserDetailsModel();
        userDetailsModel.setId(user.getId());
        userDetailsModel.setFirstName(user.getFirstName());
        userDetailsModel.setLastName(user.getLastName());
        userDetailsModel.setEmail(user.getEmail());
        userDetailsModel.setMobileNo(user.getMobileNo());
        if(user.getQrCode() != null){
            userDetailsModel.setQrCodePath(user.getQrCode().getQrCodePath());
        }else{
            userDetailsModel.setQrCodePath("");
        }

        return userDetailsModel;
    }
}
