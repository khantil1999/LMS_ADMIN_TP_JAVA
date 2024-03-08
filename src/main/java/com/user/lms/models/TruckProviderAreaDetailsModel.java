package com.user.lms.models;

import com.user.lms.entity.Taluka;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TruckProviderAreaDetailsModel {

    private List<TalukaModel> talukaList = new ArrayList<>();
    private List<DistrictModel> districtList = new ArrayList<>();
}
