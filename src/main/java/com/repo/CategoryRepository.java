package com.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByCategoryName(String categoryName);

}
