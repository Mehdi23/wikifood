package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.Category;

public interface CategoryDao {

	void saveCategory(Category Category);
	
	List<Category> findAllCategorys();
	
	List<Category> findCategory(int id);
	
	void deleteCategory(Category Category);
	
	void updateCategory(Category Category);
}
