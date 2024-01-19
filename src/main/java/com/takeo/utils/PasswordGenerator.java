package com.takeo.utils;

import java.util.Random;

public class PasswordGenerator {
	
	public static String generateRandomPassword() {
		StringBuilder result=new StringBuilder();
		String letters="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int index = random.nextInt(letters.length());
			result.append(letters.charAt(index));
		}
		return result.toString();
	}

}
