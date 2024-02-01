package com.takeo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "comment")
@Data
public class Comment {
	@Id
	@Column(name = "cid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cid;

	@Column(name = "content")
	private String content;

	@ManyToOne
	@JoinColumn(name = "pid")
	@JsonIgnore
	private Post post;
	
	@ManyToOne
	@JoinColumn(name = "uid")
	@JsonIgnore
	private User user;
	
}