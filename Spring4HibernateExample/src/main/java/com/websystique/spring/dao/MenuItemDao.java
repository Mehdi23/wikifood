package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.MenuItem;

public interface MenuItemDao {

	void saveMenuItem(MenuItem MenuItem);
	
	List<MenuItem> findAllMenuItems(MenuItem MenuItem);
	
	List<MenuItem> findAllMenuItems();
	
	void deleteMenuItem(MenuItem MenuItem);
	
	MenuItem findByName(String nom1);
	
	void updateMenuItem(MenuItem MenuItem);
}
