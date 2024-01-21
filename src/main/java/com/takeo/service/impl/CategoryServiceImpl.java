package com.takeo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.takeo.dto.CategoryDto;
import com.takeo.entity.Category;
import com.takeo.repo.CategoryRepo;
import com.takeo.repo.PostRepo;
import com.takeo.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo catDaoImpl;
	
	private PostRepo postDaoImpl;
	
	@Override
	public String create(CategoryDto category) {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public List<Category> readAll(Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category readCategory(Long categoryId, Long pid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Category update(CategoryDto category, Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(Long categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

}
