package com.takeo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.takeo.entity.User;
import com.takeo.repo.UserRepo;
import com.takeo.service.UserService;
import com.takeo.utils.OtpGenerator;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo daoImpl;

	@Override
	public User register(User user) {
		//create a otp
		String otp=OtpGenerator.generate();
	
		//send otp
		
		//save the otp for the user
		user.setOtp(otp);
		//save the user
		return daoImpl.save(user);
	}

	@Override
	public List<User> read() {
		List<User> users = daoImpl.findAll();
		return users;
	}

	@Override
	public User read(Long uid) {
		return daoImpl.findById(uid).get();
	}

	@Override
	public User update(User user) {
		User saveUser = register(user);
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

	@Override
	public String verifyOtp(String otp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String userLogin(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forgotPassword(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateProfilePicture(MultipartFile file, String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] viewProfilePicture(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
