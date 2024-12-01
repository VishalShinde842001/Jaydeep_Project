package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Product findByProductName(String productName);

	@Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.category.categoryId = :categoryId")
	boolean isCategoryUsedInProducts(@Param("categoryId") Long categoryId);

}
