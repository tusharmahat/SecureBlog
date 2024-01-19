package com.takeo.dto;

import lombok.Data;

@Data
public class PostDto {

		private Long pid;

		private String title;
		
		private String category;
		
		private String content;

		private  Long uid;

}
