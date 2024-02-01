package com.takeo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.takeo.entity.User;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	Optional<User> findByOtp(String otp);

	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);
}
