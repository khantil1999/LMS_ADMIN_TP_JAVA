package com.user.lms.repository;

import com.user.lms.entity.Taluka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TalukaRepository  extends JpaRepository<Taluka,Long> {

    @Query(value = "SELECT * FROM taluka WHERE district_id IN (:ids)", nativeQuery = true)
    List<Taluka> loadTalukaByDistrict(@Param("ids") String[] districtIds);
}
