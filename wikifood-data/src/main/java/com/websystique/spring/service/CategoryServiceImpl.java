package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.CategoryDao;
import com.websystique.spring.model.Category;

@Service("CategoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryDao dao;
	
	public void saveCategory(Category Category) {
		dao.saveCategory(Category);
	}

	
	public List<Category> findAllCategorys(int id) {
		return dao.findAllCategorys(id);
	}
	
	public List<Category> findAllCategorys() {
		return dao.findAllCategorys();
	}
	
	public void deleteCategory(Category Category){
		dao.deleteCategory(Category);
	}

	public void updateCategory(Category Category){
		dao.updateCategory(Category);
	}

}
