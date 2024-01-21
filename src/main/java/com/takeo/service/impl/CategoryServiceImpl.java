package com.takeo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.takeo.dto.CategoryDto;
import com.takeo.entity.Category;
import com.takeo.exceptions.ResourceNotFoundException;
import com.takeo.repo.CategoryRepo;
import com.takeo.repo.PostRepo;
import com.takeo.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo catDaoImpl;

	@Autowired
	private PostRepo postDaoImpl;

	@Override
	public String create(CategoryDto category) {
		Category cat = new Category();
		BeanUtils.copyProperties(category, cat);
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
		Optional<Category> readCategory = catDaoImpl.findById(categoryId);
		if (readCategory.isPresent()) {
			CategoryDto returnCategory = readCategory.get();
			
			return returnCategoryDto;
		}

		throw new ResourceNotFoundException("category with "+categoryId+" not found");
	}

	@Override
	public String update(CategoryDto category, Long categoryId) {
		// TODO Auto-generated method stub
		Optional<Category> existingCategory =catDaoImpl.findById(category.getCategoryId());
		
		if(existingCategory.isPresent())
		{
			Category c = existingCategory.get();
			
			category.setCategoryId(c.getCategoryId());
			BeanUtils.copyProperties(category, c);
			Category saveCat= catDaoImpl.save(c);
			
			CategoryDto catDto= new CategoryDto();
			BeanUtils.copyProperties(saveCat, catDto);
			return catDto;
		}
		throw new ResourceNotFoundException("Category not found");
	}

	@Override
	public String delete(Long categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

}
