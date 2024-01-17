package com.takeo.service;

import java.util.List;

import com.takeo.entity.User;

public interface UserService {

	User create(User user);

	List<User> read();

	User read(Long uid);

}
