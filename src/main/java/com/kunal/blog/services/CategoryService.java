package com.kunal.blog.services;

import java.util.List;
import java.util.Map;

import com.kunal.blog.payloads.CategoryDto;

public interface CategoryService {

	// create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updatecategory(CategoryDto categoryDto, Integer categoryId);
	
	//delete
     void deleteCategory(Integer categoryId);
	
	//get
	 CategoryDto getCategory(Integer categoryId);
	 
	//getAll
	 List<CategoryDto> getAllCategory();
	 
	 CategoryDto partialUpdate(Map<String, Object>fields,Integer Id);
}
