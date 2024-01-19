package com.takeo.utils;

import org.springframework.stereotype.Service;

@Service
public class ImageNameGenerator {
	public String getFileExtensionName(String fileName) {
		int dotIndex = fileName.lastIndexOf(".");
		if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
			return fileName.substring(dotIndex);
		}
		return "";
	}
}
