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
import com.model.Product;
import com.repo.CategoryRepository;
import com.repo.ProductRepository;
import com.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Response addProduct(Product product) throws Exception {
		Response response = new Response();
		try {
			if (product == null || product.getProductName() == null || product.getProductName().isEmpty()) {
				throw new CustomException(ErrorConstants.REQUIRED_FIELD_MISSING, "Product name is required");
			}

			Product existingProductByName = productRepository.findByProductName(product.getProductName());

			if (null != existingProductByName) {
				throw new CustomException(ErrorConstants.ALREADY_EXISTS,
						"Product Name Already Exist.. Do With Another Name");
			}
			productRepository.save(product);

		} catch (

		Exception e) {
			throw e;
		}
		return response;
	}

	@Override
	public Product getProductById(Long productId) throws Exception {
		try {
			if (null == productId || productId <= 0) {
				throw new CustomException(ErrorConstants.REQUIRED_FIELD_MISSING, "Product Id Missing...");
			}
			Optional<Product> prod = productRepository.findById(productId);
			if (prod.isPresent()) {
				return prod.get();
			} else {
				throw new CustomException(ErrorConstants.NOT_FOUND, "Product Not Found...");
			}

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteProduct(Long productId) throws Exception {
		try {
			if (null == productId || productId <= 0) {
				throw new CustomException(ErrorConstants.REQUIRED_FIELD_MISSING, "Product Id Missing...");
			}
			productRepository.deleteById(productId);

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Response updateProduct(Product product, Long productId) throws Exception {
		Response response = new Response();
		try {
			if (null == productId || productId <= 0) {
				throw new CustomException(ErrorConstants.REQUIRED_FIELD_MISSING, "Product Id Missing...");
			}
			if (null == product) {
				throw new CustomException(ErrorConstants.REQUIRED_FIELD_MISSING, "Information Not Found..");
			}

			Product existingProductByName = productRepository.findByProductName(product.getProductName());

			if (null != existingProductByName && existingProductByName.getProductId() != productId) {
				throw new CustomException(ErrorConstants.ALREADY_EXISTS, "Product Name Alerady Exist...");
			}

			Product existingProduct = productRepository.findById(productId)
					.orElseThrow(() -> new CustomException(ErrorConstants.NOT_FOUND, "Product Not Found..."));

			existingProduct.setProductName(product.getProductName());
			existingProduct.setPrice(product.getPrice());
			existingProduct.setDescription(product.getDescription());
			if (product.getCategoryId() != null) {
				existingProduct.setCategory(categoryRepository.findById(product.getCategoryId()).get());
			} else if (product.getCategory() != null) {
				existingProduct.setCategory(product.getCategory());
			}
			productRepository.save(existingProduct);

		} catch (Exception e) {
			throw e;
		}
		return response;
	}

	@Override
	public Page<Product> getProducts(int page, int size) throws Exception {
		Pageable pageable = PageRequest.of(page, size);
		return productRepository.findAll(pageable);

	}

}
