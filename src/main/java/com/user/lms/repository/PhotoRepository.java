package com.user.lms.repository;

import com.user.lms.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,Long> {

    @Query(value = "Select * from photos where vehicle_id=?",nativeQuery = true)
    List<Photo> getPhotoByVehicle(String vehicleId);

    @Modifying
    @Query(value = "DELETE FROM photos WHERE vehicle_id = ?", nativeQuery = true)
    void deleteByVehicleId(long vehicleId);
}
