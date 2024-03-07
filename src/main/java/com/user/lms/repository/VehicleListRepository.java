package com.user.lms.repository;

import com.user.lms.entity.Photo;
import com.user.lms.entity.VehicleList;
import com.user.lms.models.VehicleListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleListRepository extends JpaRepository<VehicleList,Long> {

    @Query(value="SELECT COUNT(v.id)  FROM vehicle_details as v ",nativeQuery = true)
    long countVehicles();

    @Query(value = "Select * from vehicle_details where booking_id=?",nativeQuery = true)
    VehicleListModel getVehicleByBooking(String bookingId);

}
