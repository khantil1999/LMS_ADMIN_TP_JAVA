package com.user.lms.models;

import com.user.lms.entity.Labour;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LabourDetailsModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private UserDetailsModel user;

    public static LabourDetailsModel fromEntity(Labour labour) {
        LabourDetailsModel labourDetailsModel = new LabourDetailsModel();
        labourDetailsModel.setId(labour.getId());
        labourDetailsModel.setFirstName(labour.getFirstName());
        labourDetailsModel.setLastName(labour.getLastName());
        labourDetailsModel.setEmail(labour.getEmail());
        labourDetailsModel.setMobile(labour.getMobile());
        if (labour.getUser() != null) {
            labourDetailsModel.setUser(UserDetailsModel.fromEntity(labour.getUser()));
        }
        return labourDetailsModel;
    }
}
