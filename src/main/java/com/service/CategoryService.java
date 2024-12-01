package com.service;

import org.springframework.data.domain.Page;

import com.bo.Response;
import com.model.Category;

public interface CategoryService {

	Response addCategory(Category category) throws Exception;

	Category getCategoryById(Long categoryId) throws Exception;

	public Page<Category> getCategories(int page, int size) throws Exception;

	Response updateCategory(Category category, Long categoryId) throws Exception;

	void deleteCategory(Long categoryId) throws Exception;

}
