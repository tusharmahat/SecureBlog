package com.takeo.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service

public class SmsService {

	@Value("${twilio.accountSid}")
	private String accountSid;

	@Value("${twilio.authToken}")
	private String authToken;

	@Value("${twilio.phoneNumber}")
	private String phoneNumber;

	public String sendSms(String to, String otp1) {
		Twilio.init(accountSid, authToken);

		// Send OTP via SMS

		String message = "Your OTP number is: " + otp1;

		Message.creator(new PhoneNumber(to), new PhoneNumber(phoneNumber),message).create();


		return "Send Sucessfully";

	}

}
