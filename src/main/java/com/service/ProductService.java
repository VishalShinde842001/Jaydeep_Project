package com.service;

import org.springframework.data.domain.Page;
import com.bo.Response;
import com.model.Product;

public interface ProductService {

	Response addProduct(Product product) throws Exception;

	Product getProductById(Long productId) throws Exception;

	Page<Product> getProducts(int page, int size) throws Exception;

	Response updateProduct(Product product, Long productId) throws Exception;

	void deleteProduct(Long productId) throws Exception;
}
