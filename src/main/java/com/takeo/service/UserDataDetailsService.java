package com.takeo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.takeo.entity.User;
import com.takeo.repo.UserRepo;

@Component
public class UserDataDetailsService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUsername(username);
		return user.map(u -> new UserDataDetails(u)).orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found"));
	}
}
