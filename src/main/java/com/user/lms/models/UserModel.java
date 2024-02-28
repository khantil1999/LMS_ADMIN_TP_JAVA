package com.user.lms.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {


    @NotEmpty(message = "First name cannot be blank!")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "First name should only contain alphabets and spaces")
    private String firstName;

    @NotEmpty(message = "Last name cannot be blank!")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Last name should only contain alphabets and spaces")
    private String lastName;

    @NotEmpty(message = "Mobile number cannot be blank!")
    @Pattern(regexp = "\\d{10}", message = "Mobile number should be exactly 10 digits and contain only numbers")
    private String mobileNo;

    @NotEmpty(message = "Email cannot be blank!")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password cannot be blank!")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must be a minimum of eight characters long and include at least one uppercase letter, one lowercase letter, one number, and one special character. Special characters allowed are @$!%*?&")
    private  String password;

    @NotEmpty(message = "Confirm password cannot be blank!")
    private String confirmPassword;

    private Boolean isVerified;

    public boolean isPasswordAndConfirmPasswordMatch() {
        return password != null && password.equals(confirmPassword);
    }

    private String emailExistError;
    private String confirmPasswordError;
}

