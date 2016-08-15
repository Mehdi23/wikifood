package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.MenuItem;

public interface MenuItemService {

	void saveMenuItem(MenuItem MenuItem);

	List<MenuItem> findAllMenuItems(MenuItem MenuItem);
	
	List<MenuItem> findAllMenuItems();

	void deleteMenuItem(MenuItem MenuItem);

	void updateMenuItem(MenuItem MenuItem);
}
