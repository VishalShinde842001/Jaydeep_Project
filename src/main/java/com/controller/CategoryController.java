package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bo.ErrorConstants;
import com.bo.Response;
import com.exception.CustomException;
import com.model.Category;
import com.service.CategoryService;

@RestController
@CrossOrigin
@RequestMapping("api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping
	public Response addCategory(@RequestBody Category category) {
		Response response = new Response();
		try {
			return categoryService.addCategory(category);

		} catch (CustomException ce) {
			response.setStatus(ce.getErrorCode());
			response.setMessage(ce.getMessage());
		} catch (Exception e) {
			response.setStatus(ErrorConstants.INTERNAL_SERVER_ERROR);
			response.setMessage(ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;

	}

	@GetMapping
	public Response getCategories(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size) {
		Response response = new Response();
		try {
			response.setResult(categoryService.getCategories(page, size));
			response.setMessage("Categories Get Sucessfully...");
			response.setStatus(ErrorConstants.SUCESS);
		} catch (CustomException ce) {
			response.setStatus(ce.getErrorCode());
			response.setMessage(ce.getMessage());
		} catch (Exception e) {
			response.setStatus(ErrorConstants.INTERNAL_SERVER_ERROR);
			response.setMessage(ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}

	@GetMapping("/{categoryId}")
	public Response getCategoryById(@PathVariable Long categoryId) {
		Response response = new Response();
		try {
			response.setResult(categoryService.getCategoryById(categoryId));
			response.setMessage("Category Get Sucessfully...");
			response.setStatus(ErrorConstants.SUCESS);
		} catch (CustomException ce) {
			response.setStatus(ce.getErrorCode());
			response.setMessage(ce.getMessage());
		} catch (Exception e) {
			response.setStatus(ErrorConstants.INTERNAL_SERVER_ERROR);
			response.setMessage(ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}

	@PutMapping("/{categoryId}")
	public Response updateCategory(@RequestBody Category category, @PathVariable Long categoryId) {
		Response response = new Response();
		try {

		} catch (CustomException ce) {
			response.setStatus(ce.getErrorCode());
			response.setMessage(ce.getMessage());
		} catch (Exception e) {
			response.setStatus(ErrorConstants.INTERNAL_SERVER_ERROR);
			response.setMessage(ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}

	@DeleteMapping("/{categoryId}")
	public Response deleteCategory(@PathVariable Long categoryId) {
		Response response = new Response();
		try {
			categoryService.deleteCategory(categoryId);
			response.setStatus(ErrorConstants.SUCESS);
			response.setMessage("Category Deleted Sucessfully...");
		} catch (CustomException ce) {
			response.setStatus(ce.getErrorCode());
			response.setMessage(ce.getMessage());
		} catch (Exception e) {
			response.setStatus(ErrorConstants.INTERNAL_SERVER_ERROR);
			response.setMessage(ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}
}
