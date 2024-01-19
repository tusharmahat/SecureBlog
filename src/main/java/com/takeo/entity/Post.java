package com.takeo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="pid")
	private Long pid;
	
	@Column(name="title")
	private String title;
	
	@Column(name="category")
	private String category;
	
	@Column(name="content")
	private String content;
	
	@ManyToOne
	@JoinColumn(name="uid")
	@JsonBackReference
	private  User user;

}
