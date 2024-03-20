package com.user.lms.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordResponseModel {
    private Boolean isError;
    private String errorMessage;
    private Boolean isOldError;
}
