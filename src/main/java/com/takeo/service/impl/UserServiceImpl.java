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
import com.takeo.entity.User;
import com.takeo.exceptions.DuplicateItemException;
import com.takeo.exceptions.InvalidFileExtensionException;
import com.takeo.exceptions.PasswordMismatchException;
import com.takeo.exceptions.ResourceNotFoundException;
import com.takeo.repo.UserRepo;
import com.takeo.service.UserService;
import com.takeo.utils.EmailService;
import com.takeo.utils.ImageFile;
import com.takeo.utils.ImageNameGenerator;
import com.takeo.utils.OtpGenerator;
import com.takeo.utils.PasswordGenerator;

@Service
public class UserServiceImpl implements UserService {

	private final String DB_PATH = "/Users/tusharmahat/db/";
	@Autowired
	private UserRepo daoImpl;

	@Autowired
	private EmailService emailService;

	@Autowired
	private ImageFile imageFile;

	@Autowired
	private ImageNameGenerator fileNameGenerator;

	@Override
	public String register(User user) {
		Optional<User> existingUser = daoImpl.findByEmail(user.getEmail());
		String message = "Registration failed";
		if (existingUser.isEmpty()) {
			// create a otp
			String otp = OtpGenerator.generate();

			// send otp
			emailService.sendMail(user.getEmail(), "OTP", "Your OTP is " + otp);

			// save the otp for the user
			user.setOtp(otp);
			message = "Registration Success";
			// save the user
			User saveUser = daoImpl.save(user);
			if (saveUser != null) {
				message = "OTP sent to respected email address";
			}
		} else {
			throw new DuplicateItemException("User with same email already registered.");
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
	public UserDto update(UserDto user) {
		Optional<User> existingUser = daoImpl.findById(user.getUId());
		if (existingUser.isPresent()) {
			User u = existingUser.get();

			user.setUId(u.getUId());
			BeanUtils.copyProperties(user, u);
			User saveUser = daoImpl.save(u);

			UserDto uDto = new UserDto();
			BeanUtils.copyProperties(saveUser, uDto);
			return uDto;
		}
		throw new ResourceNotFoundException("User not found");// exception
	}

	@Override
	public boolean delete(Long uid) {
		Optional<User> existingUser = daoImpl.findById(uid);

		if (existingUser.isPresent()) {
			daoImpl.deleteById(uid);
			return true;
		} else {
			throw new ResourceNotFoundException("User with uid: " + uid + "not found ");
		}
	}

	@Override
	public String verifyOtp(String otp) {
		Optional<User> existingUser = daoImpl.findByOtp(otp);
		String message = "OTP did not match";
		if (existingUser.isPresent()) {
			User user = existingUser.get();
			if (otp.equals(user.getOtp())) {
				String randomPassword = PasswordGenerator.generateRandomPassword();
				user.setPassword(randomPassword);
				user.setOtp("");
				user.setRoleId(2l);
				User saveUser = daoImpl.save(user);
				if (saveUser != null) {
					emailService.sendMail(user.getEmail(), "Password", "Your password is " + randomPassword);
					message = "OTP verified, password sent to email";
				}
			}
			return message;
		}
		throw new ResourceNotFoundException("User not found");
	}

	@Override
	public String userLogin(String email, String password) {
		Optional<User> existingUser = daoImpl.findByEmail(email);
		if (existingUser.isPresent()) {
			User user = existingUser.get();
			if (user.getPassword() != null) {
				if (user.getPassword().equals(password)) {
					return "Logged in";
				} else {
					throw new PasswordMismatchException("Password did not match");
				}
			}
			return "OTP not verified";
		}
		throw new ResourceNotFoundException("User not found with email: " + email);
	}

	@Override
	public String forgotPassword(String email) {
		Optional<User> user = daoImpl.findByEmail(email);
		String message = "Failed to change password";
		if (!user.isEmpty()) {
			User existingUser = user.get();
			String randomPassword = PasswordGenerator.generateRandomPassword();
			existingUser.setPassword(randomPassword);
			User saveUser = daoImpl.save(existingUser);
			if (saveUser != null) {
				emailService.sendMail(existingUser.getEmail(), "Password", "Your password is " + randomPassword);
				message = "New password sent to respective email";
			}
			return message;
		}
		throw new ResourceNotFoundException("User not found with email: " + email);
	}

	@Override
	public String updateProfilePicture(MultipartFile file, String email) {
		Optional<User> existingUser = daoImpl.findByEmail(email);
		if (!existingUser.isEmpty()) {
			User user = existingUser.get();
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
				user.setImage(filePath);
				User updatePhoto = daoImpl.save(user);
				if (updatePhoto != null) {
					return "successfull";
				}
			} else {
				throw new InvalidFileExtensionException("Only image files are allowed.");
			}
		}
		throw new ResourceNotFoundException("User not found with email: " + email);
	}

	@Override
	public byte[] viewProfilePicture(String email) {
		Optional<User> user = daoImpl.findByEmail(email);
		if (user.isPresent()) {
			String filePath = user.get().getImage();
			if (filePath != null) {
				try {
					return Files.readAllBytes(Paths.get(filePath));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				throw new ResourceNotFoundException("Profile picture not found for the user with email: " + email);
			}
		}
		throw new ResourceNotFoundException("User not found with email: " + email);
	}

	@Override
	public String changePassword(ResetPasswordDto resetPassDto) {
		Optional<User> user = daoImpl.findByEmail(resetPassDto.getEmail());
		if (user != null) {
			if (resetPassDto.getPassword().equals(resetPassDto.getConfirmPassword())) {
				User userObject = user.get();
				userObject.setPassword(resetPassDto.getConfirmPassword());

				User saveUser = daoImpl.save(userObject);
				if (saveUser != null) {
					return "Password updated";
				}
			} else {
				throw new PasswordMismatchException("Password mismatch");
			}
		}
		throw new ResourceNotFoundException("User not found with email: " + resetPassDto.getEmail());
	}

}
