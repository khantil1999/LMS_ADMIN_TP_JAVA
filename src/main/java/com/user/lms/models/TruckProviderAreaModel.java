package com.user.lms.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TruckProviderAreaModel {
    private long id;
    private TalukaModel talukaModel;
    private DistrictModel districtModel;
}
