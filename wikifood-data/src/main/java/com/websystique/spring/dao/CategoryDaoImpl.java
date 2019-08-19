package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.Category;

@Repository("CategoryDao")
public class CategoryDaoImpl extends AbstractDao implements CategoryDao {

	/*private static final Logger logger = LogManager.getLogger("HelloWorld");*/

	public void saveCategory(Category Category) {
		Category.getProductTypelist().forEach(producttype->producttype.setCategory(Category));
		persist(Category);
	}

	@SuppressWarnings("unchecked")
	public List<Category> findCategory(int id) {
		Criteria criteria = getSession().createCriteria(Category.class)
				.add(Restrictions.eq("id",id));
		return (List<Category>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Category> findAllCategorys() {
		Criteria criteria = getSession().createCriteria(Category.class);
		return (List<Category>) criteria.list();
	}


	public void deleteCategory(Category Category) {
		getSession().delete(Category);
	}
	
	public void updateCategory(Category Category) {
		Category.getProductTypelist().forEach(producttype->producttype.setCategory(Category));
		getSession().update(Category);
		   
	}

}
