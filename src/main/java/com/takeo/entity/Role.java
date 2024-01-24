package com.takeo.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rid")
	private Long roleId;
	@Column(name = "role")
	private String role;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "rolesUsers", joinColumns = @JoinColumn(name = "rid", referencedColumnName = "rid"), inverseJoinColumns = @JoinColumn(name = "uid", referencedColumnName = "uid"))
	private List<User> rolesUsers = new ArrayList<>();
}
