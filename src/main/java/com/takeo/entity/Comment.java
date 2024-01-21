package com.takeo.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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