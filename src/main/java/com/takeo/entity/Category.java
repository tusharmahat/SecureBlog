package com.takeo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "Category")
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "catId")
	private Long categoryId;

	@Column(name = "categoryName")
	private String categoryName;

	@ManyToMany
	@JoinTable(
	    name = "categoriesPosts",
	    joinColumns = @JoinColumn(name = "cat_id", referencedColumnName = "catId"),
	    inverseJoinColumns = @JoinColumn(name = "p_id", referencedColumnName = "pid")
	)
	List<Post> categoriesPosts = new ArrayList<>();
}
