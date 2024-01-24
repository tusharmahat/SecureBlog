package com.takeo.dto;

import java.util.List;

import com.takeo.entity.Category;
import com.takeo.entity.Comment;
import com.takeo.entity.Post;
import com.takeo.entity.Role;
import com.takeo.entity.User;

import lombok.Data;

@Data
public class ReportDto {
	private List<Role> roles;
	private List<User> users;
	private List<Category> categories;
	private List<Post> posts;
	private List<Comment> comments;
}