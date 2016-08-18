package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.MenuItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Repository("menuitemDao")
public class MenuItemDaoImpl extends AbstractDao implements MenuItemDao {

	private static final Logger logger = LogManager.getLogger("HelloWorld");

	public void saveMenuItem(MenuItem MenuItem) {
		persist(MenuItem);
	}

	@SuppressWarnings("unchecked")
	public List<MenuItem> findAllMenuItems(MenuItem MenuItem) {
		//System.out.println("Fr : " + MenuItem.getItemLabelEn() + " En : " + MenuItem.getItemLabelFr());
		Criteria criteria = getSession().createCriteria(MenuItem.class);
		return (List<MenuItem>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<MenuItem> findAllMenuItems() {
		//System.out.println("Fr : " + MenuItem.getItemLabelEn() + " En : " + MenuItem.getItemLabelFr());
		Criteria criteria = getSession().createCriteria(MenuItem.class);
		return (List<MenuItem>) criteria.list();
	}

	public void deleteMenuItem(MenuItem MenuItem) {
		getSession().delete(MenuItem);
	}
	
	public void updateMenuItem(MenuItem MenuItem) {
		getSession().update(MenuItem);
		   
	}

	public MenuItem findByName(String item) {
		Criteria criteria = getSession().createCriteria(MenuItem.class);
		criteria.add(Restrictions.eq("item", item));
		return (MenuItem) criteria.uniqueResult();
	}

}
