package com.user.lms.repository;

import com.user.lms.entity.Labour;
import com.user.lms.entity.VehicleList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface LabourRepository extends JpaRepository<Labour,Long> {

    @Query("SELECT l from Labour l where l.user.id= ?1")
    List<Labour> getAllByTruckProvider(Long id);

    @Query("SELECT veh from Labour veh where veh.user.id = ?1")
    List<VehicleList> getLabourersByTP(Long id);
}
