package com.takeo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeo.entity.User;
import com.takeo.repo.UserRepo;
import com.takeo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo daoImpl;

	@Override
	public User create(User user) {
		return daoImpl.save(user);
	}

	@Override
	public List<User> read() {
		List<User> users = daoImpl.findAll();
		return users;
	}

	@Override
	public User read(Long uid) {
		System.out.println("UID " + uid);
		return daoImpl.findById(uid).get();
	}

	@Override
	public User update(User user) {
		User saveUser = create(user);
		return saveUser;
	}

	@Override
	public boolean delete(Long uid) {
		User existingUser = read(uid);

		if (existingUser != null) {
			daoImpl.deleteById(uid);
			return true;
		}
		return false;
	}

}
