package com.takeo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.takeo.dto.ResetPasswordDto;
import com.takeo.dto.UserDto;

public interface UserService {

	String register(UserDto user);

	List<UserDto> read();

	String update(UserDto user);

	public String verifyOtp(String otp);

	public String userLogin(String email, String password);

	public String forgotPassword(String email);

	public String changePassword(ResetPasswordDto resetPassDto);

	public String updateProfilePicture(MultipartFile file, String email);

	public byte[] viewProfilePicture(String email);

}
