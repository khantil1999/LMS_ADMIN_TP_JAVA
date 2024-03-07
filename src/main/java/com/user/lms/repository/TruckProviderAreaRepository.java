package com.user.lms.repository;

import com.user.lms.entity.TruckProviderArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TruckProviderAreaRepository extends JpaRepository<com.user.lms.entity.TruckProviderArea,Long> {

    @Query("SELECT tpa FROM TruckProviderArea tpa WHERE tpa.truckProvider.id = ?1")
    List<TruckProviderArea> getTruckProviderAreaById(long id);

    @Modifying
    @Query("DELETE  FROM TruckProviderArea tpa WHERE tpa.truckProvider.id = ?1 and tpa.taluka.id in (?2)")
    void deleteTruckProviderAreaByTalukaAndTruckProvider(long id, List<Long> ids);


}
