package com.user.lms.repository;

import com.user.lms.entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode,Long> {


    @Modifying
    @Query("DELETE FROM QrCode qr where qr.truckProvider.id = ?1")
    void deleteQrCodeByTruckProvider(Long id);

}
