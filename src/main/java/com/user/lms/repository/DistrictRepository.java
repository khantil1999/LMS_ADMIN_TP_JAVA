package com.user.lms.repository;

import com.user.lms.entity.District;
import com.user.lms.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District,Long> {
}
