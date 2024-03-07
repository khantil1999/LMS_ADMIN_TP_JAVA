package com.user.lms.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class DistrictModel {
    private long id;
    private String districtName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistrictModel that = (DistrictModel) o;
        return id == that.id && Objects.equals(districtName, that.districtName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, districtName);
    }
}
