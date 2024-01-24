package com.takeo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeo.dto.CategoryDto;
import com.takeo.entity.Category;
import com.takeo.exceptions.DuplicateItemException;
import com.takeo.exceptions.ResourceNotFoundException;
import com.takeo.repo.CategoryRepo;
import com.takeo.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CategoryRepo catDaoImpl;

	@Override
	public String create(CategoryDto categoryDto) {
		if (catDaoImpl.findByCategoryName(categoryDto.getCategoryName()).isPresent()) {
			throw new DuplicateItemException("The category " + categoryDto.getCategoryName() + " already exists");
		}
		Category cat = new Category();
		BeanUtils.copyProperties(categoryDto, cat);
		Category saveCategory = catDaoImpl.save(cat);
		String message = "Cannot Create Category";
		if (saveCategory != null) {
			message = "Category created";
			return message;
		}
		return message;
	}

	@Override
	public List<CategoryDto> readAll() {

		List<Category> category = catDaoImpl.findAll();
		List<CategoryDto> categoryDto = new ArrayList<>();
		if (category != null) {
			for (Category c : category) {
				CategoryDto catDto = new CategoryDto();
				BeanUtils.copyProperties(c, catDto);
				categoryDto.add(catDto);
			}
			return categoryDto;
		}
		throw new ResourceNotFoundException("No catogories found");
	}

	@Override
	public CategoryDto readCategory(Long categoryId) {
		Category readCategory = catDaoImpl.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category with " + categoryId + " not found"));

		CategoryDto catDto = new CategoryDto();

		BeanUtils.copyProperties(readCategory, catDto);

		return catDto;
	}

	@Override
	public String update(CategoryDto category, Long categoryId) {
		String message = "Not updated";
		Category existingCategory = catDaoImpl.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with category id: " + categoryId));
		category.setCategoryId(existingCategory.getCategoryId());
		modelMapper.map(category, existingCategory);
		Category saveCat = catDaoImpl.save(existingCategory);
		if (saveCat != null) {
			message = "Updated";
		}
		return message;

	}

	@Override
	public String delete(Long categoryId) {
		String message = "Category not deleted";
		catDaoImpl.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with category id: " + categoryId));
		catDaoImpl.deleteById(categoryId);
		message = "Category deleted";
		return message;
	}
}
