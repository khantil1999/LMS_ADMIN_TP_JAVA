package com.user.lms.repository;

import com.user.lms.entity.VehicleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleListRepository extends JpaRepository<VehicleList,Long> {

    @Query(value="SELECT COUNT(v.id)  FROM vehicle_details as v ",nativeQuery = true)
    long countVehicles();

    @Query("SELECT veh from VehicleList veh where veh.driver.id = ?1")
    List<VehicleList> getVehicleListByTP(Long id);

}
