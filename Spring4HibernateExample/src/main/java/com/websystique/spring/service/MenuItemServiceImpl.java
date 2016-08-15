package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.MenuItemDao;
import com.websystique.spring.model.MenuItem;

@Service("MenuItemService")
@Transactional
public class MenuItemServiceImpl implements MenuItemService{

	@Autowired
	private MenuItemDao dao;
	
	public void saveMenuItem(MenuItem MenuItem) {
		dao.saveMenuItem(MenuItem);
	}
	
	public List<MenuItem> findAllMenuItems() {
		return dao.findAllMenuItems();
	}

	public List<MenuItem> findAllMenuItems(MenuItem MenuItem) {
		return dao.findAllMenuItems(MenuItem);
	}
	
	public void deleteMenuItem(MenuItem MenuItem) {
		dao.deleteMenuItem(MenuItem);
	}


	public void updateMenuItem(MenuItem MenuItem){
		dao.updateMenuItem(MenuItem);
	}
}
