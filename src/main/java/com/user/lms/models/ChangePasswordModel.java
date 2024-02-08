package com.user.lms.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangePasswordModel {

    @NotEmpty(message = "Password cannot be blank!")
    private String oldPassword;

    @NotEmpty(message = "New Password cannot be blank!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be a minimum of eight characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character. Special characters allowed are @$!%*?&")
    private  String newPassword;

    @NotEmpty(message = "Confirm password cannot be blank!")
    private String confirmPassword;

    private String email;

    private Long userId;

    private String passwordError;

    public boolean isPasswordAndConfirmPasswordMatch() {
        return newPassword != null && newPassword.equals(confirmPassword);
    }
}
