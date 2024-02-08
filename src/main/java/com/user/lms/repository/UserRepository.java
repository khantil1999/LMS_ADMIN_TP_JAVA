package com.user.lms.repository;

import com.user.lms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	@Query(value = "select * from users where email = ? and is_verified = ?",nativeQuery = true)
	User findByEmail(String email,Boolean isVerified);

	@Query(value = "select * from users where email = ?",nativeQuery = true)
	User findExistingUser(String email);

	@Query(value="SELECT COUNT(u.id) as userCount FROM users as u JOIN user_roles as r ON u.id = r.user_id WHERE r.role_id = ?",nativeQuery = true)
	long countUsers(Long roleId);

	@Query(value = "SELECT us.* FROM users us JOIN user_roles ur ON us.id=ur.user_id  where ur.role_id=?",nativeQuery = true)
	List<User> findAllByRoleId(Long roleId);

	@Modifying
	@Query(value="Update users set is_approved=? where id = ?",nativeQuery = true)
	void approvedDisApprovedTruckProvider(Boolean approve, Long id);

	@Query(value = "delete * from users where id=?",nativeQuery = true)
	void deleteLaborers(Long id);
}
