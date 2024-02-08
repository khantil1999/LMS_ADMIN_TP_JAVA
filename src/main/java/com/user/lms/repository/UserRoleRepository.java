package com.user.lms.repository;

import com.user.lms.entity.Role;
import com.user.lms.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRoleRepository extends JpaRepository<UserRoles, Long> {

    @Modifying
    @Query(value = "DELETE FROM user_roles where user_id = ?",nativeQuery = true)
    void deleteUserRolesByUserId(Long userId);
}
