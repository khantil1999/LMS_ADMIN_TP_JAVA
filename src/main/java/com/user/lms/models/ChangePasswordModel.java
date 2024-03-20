package com.user.lms.models;

import lombok.Data;

@Data
public class ChangePasswordModel {


    private String oldPassword;


    private  String newPassword;


    private String confirmPassword;


    public boolean isPasswordAndConfirmPasswordMatch() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
}
