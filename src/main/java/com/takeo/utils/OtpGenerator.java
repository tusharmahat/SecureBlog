package com.takeo.utils;

import java.util.Random;

public class OtpGenerator {

	public static String generate() {
		String otp = "";
		Random rand = new Random();
		for (int i = 0; i < 6; i++) {
			otp += rand.nextInt(9) + 1; // [0,9)+1
		}
		return otp;
	}
}
