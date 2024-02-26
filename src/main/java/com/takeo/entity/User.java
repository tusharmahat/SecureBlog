package com.takeo.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name = "Users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uid")
	private Long uId;

	@Column(name = "name")
	private String name;

	@Column(name = "username")
	private String username;

	@Column(name = "email")
	private String email;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "password")
	private String password;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date creationDate = new Date();

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable = false)
	private Date updateDate = new Date();

	@JsonIgnore
	private int age;

	@JsonIgnore
	private String gender;

	@JsonIgnore
	private String image;

	@JsonIgnore
	private String otp;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Post> posts = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Comment> comments = new ArrayList<>();

	@ManyToMany(mappedBy = "rolesUsers", cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private List<Role> roles = new ArrayList<>();

}
