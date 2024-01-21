package com.takeo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="Category")
public class Category {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="cid")
private Long categoryId;

@Column(name="cTitle")
private String categoryTitle;

@Column(name="cDiscription")
private String CategoryDiscription;

@OneToMany(mappedBy="category", cascade=CascadeType.ALL)
List<Post> posts =new ArrayList<>();

}
