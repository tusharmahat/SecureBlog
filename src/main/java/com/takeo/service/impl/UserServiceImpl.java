package com.takeo.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.takeo.dto.ResetPasswordDto;
import com.takeo.dto.UserDto;
import com.takeo.entity.Role;
import com.takeo.entity.User;
import com.takeo.exceptions.DuplicateItemException;
import com.takeo.exceptions.InvalidFileExtensionException;
import com.takeo.exceptions.PasswordMismatchException;
import com.takeo.exceptions.ResourceNotFoundException;
import com.takeo.repo.RoleRepo;
import com.takeo.repo.UserRepo;
import com.takeo.service.UserService;
import com.takeo.utils.EmailService;
import com.takeo.utils.ImageFile;
import com.takeo.utils.ImageNameGenerator;
import com.takeo.utils.OtpGenerator;
import com.takeo.utils.PasswordGenerator;
import com.takeo.utils.SmsService;

@Service
public class UserServiceImpl implements UserService {

	private final String DB_PATH = "/Users/tusharmahat/db/";
//	private final String DB_PATH ="C:\\Users\\himal\\OneDrive\\Desktop\\db\\";
	@Autowired
	private UserRepo daoImpl;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SmsService smsService; 

	@Autowired
	private RoleRepo roleDaoImpl;

	@Autowired
	private ImageFile imageFile;

	@Autowired
	private ImageNameGenerator fileNameGenerator;
  
	@Override
	public String register(UserDto userDto) {
		Optional<User> existingUser = daoImpl.findByEmail(userDto.getEmail());

		String message = "Registration failed";
		if (existingUser.isPresent()) {
			throw new DuplicateItemException("User with same email already registered.");
		} else {
			// create a otp
			String otp = OtpGenerator.generate();

			try {
				// Send OTP via email
				emailService.sendMail(userDto.getEmail(), "OTP", "Your OTP is " + otp);
				smsService.sendSms(userDto.getMobile(), "Your OTP is " + otp);
			} catch (Exception e) {
				// Handle email sending failure (log or provide a user-friendly message)
				return "Failed to send OTP. Please try again.";
			}

			User user = new User();
			BeanUtils.copyProperties(userDto, user);

			Role role = roleDaoImpl.findByRole("User").orElseThrow(
					() -> new ResourceNotFoundException("Default roles initializer not working, roles not found"));

			role.getRolesUsers().add(user);
			user.getRoles().add(role);

			// Save the OTP for the user
			user.setOtp(otp);

			// Save the user
			User saveUser = daoImpl.save(user);
			if (saveUser != null) {
				message = "OTP sent to the respective email address/Phone Number";
			}
		}
		return message;
	}

	@Override
	public List<UserDto> read() {
		List<User> users = daoImpl.findAll();
		List<UserDto> usersDto = new ArrayList<>();
		for (User u : users) {
			UserDto uDto = new UserDto();
			BeanUtils.copyProperties(u, uDto);
			usersDto.add(uDto);
		}

		if (users.isEmpty()) {
			throw new ResourceNotFoundException("Users not found");
		}
		return usersDto;
	}

	@Override
	public String update(UserDto user) {
		User existingUser = daoImpl.findByEmail(user.getEmail())
				.orElseThrow(() -> new DuplicateItemException("User not found."));
		String message = "User not updated";

		user.setUId(existingUser.getUId());
		BeanUtils.copyProperties(user, existingUser);
		User saveUser = daoImpl.save(existingUser);
		if (saveUser != null) {
			message = "User updated";
		}
		return message;
	}

	@Override
	public String verifyOtp(String otp) {
		User existingUser = daoImpl.findByOtp(otp).orElseThrow(() -> new DuplicateItemException("User not found."));
		String message = "OTP did not match";

		if (otp.equals(existingUser.getOtp())) {
			String randomPassword = PasswordGenerator.generateRandomPassword();
			existingUser.setPassword(randomPassword);
			existingUser.setOtp("");
			User saveUser = daoImpl.save(existingUser);
			if (saveUser != null) {
				emailService.sendMail(existingUser.getEmail(), "Password", "Your password is " + randomPassword);
				message = "OTP verified, password sent to email";
			}
		}
		return message;
	}

	@Override
	public String userLogin(String email, String password) {
		User existingUser = daoImpl.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
		if (existingUser.getPassword() != null) {
			if (existingUser.getPassword().equals(password)) {
				return "Logged in";
			} else {
				throw new PasswordMismatchException("Password did not match");
			}
		}
		return "OTP not verified";
	}

	@Override
	public String forgotPassword(String email) {
		User user = daoImpl.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
		String message = "Failed to change password";

		String randomPassword = PasswordGenerator.generateRandomPassword();
		user.setPassword(randomPassword);
		User saveUser = daoImpl.save(user);
		if (saveUser != null) {
			emailService.sendMail(user.getEmail(), "Password", "Your password is " + randomPassword);
			message = "New password sent to respective email";
		}
		return message;
	}

	@Override
	public String updateProfilePicture(MultipartFile file, String email) {
		User existingUser = daoImpl.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
		String fileName = timestamp + fileNameGenerator.getFileExtensionName(file.getOriginalFilename());
		String filePath = DB_PATH + fileName;

		if (imageFile.isImageFile(file)) {
			// Create the folder if it doesn't exist
			File folder = new File(filePath);
			if (!folder.exists()) {
				folder.mkdirs();
			}
			try {
				file.transferTo(new File(filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
			existingUser.setImage(filePath);
			User updatePhoto = daoImpl.save(existingUser);
			if (updatePhoto != null) {
				return "Profile Picture uploaded";
			}
		}
		throw new InvalidFileExtensionException("Only image files are allowed.");
	}

	@Override
	public byte[] viewProfilePicture(String email) {
		User user = daoImpl.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

		String filePath = user.getImage();
		if (filePath != null) {
			try {
				return Files.readAllBytes(Paths.get(filePath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		throw new ResourceNotFoundException("Profile picture not found for the user with email: " + email);
	}

	@Override
	public String changePassword(ResetPasswordDto resetPassDto) {
		User user = daoImpl.findByEmail(resetPassDto.getEmail()).orElseThrow(
				() -> new ResourceNotFoundException("User not found with email: " + resetPassDto.getEmail()));
		if (resetPassDto.getPassword().equals(resetPassDto.getConfirmPassword())) {

			user.setPassword(resetPassDto.getConfirmPassword());

			User saveUser = daoImpl.save(user);
			if (saveUser != null) {
				return "Password updated";
			}
		}
		throw new PasswordMismatchException("Password mismatch");
	}
}
