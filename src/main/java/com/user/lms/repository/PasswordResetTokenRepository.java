package com.user.lms.repository;

import com.user.lms.entity.PasswordResetToken;
import com.user.lms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    @Query(value="select * from password_reset_token where token=?",nativeQuery = true)
    PasswordResetToken findDetailsByToken(String token);

    @Modifying
    @Query(value = "delete from password_reset_token where user_id=?",nativeQuery = true)
    void deleteTokenByUser(Long userId);


}
