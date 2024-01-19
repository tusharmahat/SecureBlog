package com.takeo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

import com.takeo.entity.User;


public interface UserRepo extends JpaRepository<User, Long> {	
	Optional<User> findByOtp(String otp);
	Optional<User> findByEmail(String email);
}
