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
import com.model.Product;
import com.service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("api/products")
public class ProductController {
	@Autowired
	private ProductService productService;

	@PostMapping
	public Response addProduct(@RequestBody Product product) {
		Response response = new Response();
		try {
			return productService.addProduct(product);

		} catch (CustomException ce) {
			response.setStatus(ce.getErrorCode());
			response.setMessage(ce.getMessage());
		} catch (Exception e) {
			response.setStatus(ErrorConstants.INTERNAL_SERVER_ERROR);
			response.setMessage(ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;

	}

	@GetMapping("/{productId}")
	public Response getProductById(@PathVariable Long productId) {
		Response response = new Response();
		try {
			response.setResult(productService.getProductById(productId));
			response.setMessage("Product Get Sucessfully...");
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

	@GetMapping
	public Response getProducts(@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10", required = false) int size) {
		Response response = new Response();
		try {
			response.setResult(productService.getProducts(page, size));
			response.setMessage("Products Get Sucessfully...");
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

	@PutMapping("/{productId}")
	public Response updateProduct(@RequestBody Product product, @PathVariable Long productId) {
		Response response = new Response();
		try {
			return productService.updateProduct(product, productId);

		} catch (CustomException ce) {
			response.setStatus(ce.getErrorCode());
			response.setMessage(ce.getMessage());
		} catch (Exception e) {
			response.setStatus(ErrorConstants.INTERNAL_SERVER_ERROR);
			response.setMessage(ErrorConstants.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		return response;
	}

	@DeleteMapping("/{productId}")
	public Response deleteCategory(@PathVariable Long productId) {
		Response response = new Response();
		try {
			productService.deleteProduct(productId);
			response.setStatus(ErrorConstants.SUCESS);
			response.setMessage("Product Deleted Sucessfully...");
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
