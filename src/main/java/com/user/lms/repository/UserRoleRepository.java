package com.user.lms.repository;

import com.user.lms.entity.Role;
import com.user.lms.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoles, Long> {
}
