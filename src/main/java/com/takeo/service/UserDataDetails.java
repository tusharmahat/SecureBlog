package com.takeo.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.takeo.entity.User;

public class UserDataDetails implements UserDetails {
	private String userName;
	private String password;
	private List<GrantedAuthority> authority;

	public UserDataDetails(User user) {
		userName = user.getUsername();
		password = user.getPassword();
		/**
		 * user : {username:"tushar",password:"1234",roles:[{rid:1,role:USER} ,{rid:2,role:ADMIN}    ]}
		 * 
		 * 
		 */
		//user.getRoles();    =>   [{rid:1,role:USER} ,{rid:2,role:ADMIN}]
		//authority=["USER","ADMIN"]
		// List<Role>listRoles=user.getRoles(); 
		//listRoles.stream().map(role->new SimpleGrantedAuthority(role.getRole()))

		authority = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole()))
				.collect(Collectors.toList());
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authority;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
