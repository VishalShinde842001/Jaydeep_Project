package com.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bo.ErrorConstants;
import com.bo.Response;
import com.exception.CustomException;
import com.model.Category;
import com.repo.CategoryRepository;
import com.repo.ProductRepository;
import com.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public Response addCategory(Category category) throws Exception {
		Response response = new Response();
		try {
			if (category == null || category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
				throw new CustomException(ErrorConstants.REQUIRED_FIELD_MISSING, "Category name is required");
			}

			Category existingCategoryByName = categoryRepository.findByCategoryName(category.getCategoryName());

			if (null != existingCategoryByName) {
				throw new CustomException(ErrorConstants.ALREADY_EXISTS,
						"Category Name Already Exist.. Do With Another Name");
			}
			categoryRepository.save(category);

		} catch (

		Exception e) {
			throw e;
		}
		return response;
	}

	@Override
	public Category getCategoryById(Long categoryId) throws Exception {
		try {
			if (null == categoryId || categoryId <= 0) {
				throw new CustomException(ErrorConstants.REQUIRED_FIELD_MISSING, "Category Id Missing...");
			}
			Optional<Category> cat = categoryRepository.findById(categoryId);
			if (cat.isPresent()) {
				return cat.get();
			} else {
				throw new CustomException(ErrorConstants.NOT_FOUND, "Category Not Found...");
			}

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteCategory(Long categoryId) throws Exception {
		try {
			if (null == categoryId || categoryId <= 0) {
				throw new CustomException(ErrorConstants.REQUIRED_FIELD_MISSING, "Category Id Missing...");
			}

			if (productRepository.isCategoryUsedInProducts(categoryId)) {
				throw new CustomException(ErrorConstants.NOT_IMPLEMENTED,
						"Category Cannot deleted because it is used in 1 on more products..");
			} else {
				categoryRepository.deleteById(categoryId);
			}

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response updateCategory(Category category, Long categoryId) throws Exception {
		Response response = new Response();
		try {
			if (null == categoryId || categoryId <= 0) {
				throw new CustomException(ErrorConstants.REQUIRED_FIELD_MISSING, "Category Id Missing...");
			}
			if (null == category) {
				throw new CustomException(ErrorConstants.REQUIRED_FIELD_MISSING, "Information Not Found..");
			}

			Category existingCategoryByName = categoryRepository.findByCategoryName(category.getCategoryName());

			if (null != existingCategoryByName && existingCategoryByName.getCategoryId() != categoryId) {
				throw new CustomException(ErrorConstants.ALREADY_EXISTS, "Category Name Alerady Exist...");
			}

			Category existingCategory = categoryRepository.findById(categoryId)
					.orElseThrow(() -> new CustomException(ErrorConstants.NOT_FOUND, "Category Not Found..."));

			existingCategory.setCategoryName(category.getCategoryName());
			existingCategory.setDescription(category.getDescription());
			categoryRepository.save(existingCategory);

		} catch (Exception e) {
			throw e;
		}
		return response;
	}

	@Override
	public Page<Category> getCategories(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return categoryRepository.findAll(pageable);
	}
}
