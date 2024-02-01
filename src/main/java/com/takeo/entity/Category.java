package com.takeo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
