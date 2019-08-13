package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.Category;

public interface CategoryService {

	void saveCategory(Category Category);

	List<Category> findCategory (int id);
	
	List<Category> findAllCategorys();

	void deleteCategory(Category Category);

	void updateCategory(Category Category);
}
