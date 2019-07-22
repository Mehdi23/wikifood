package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.Product;

@Repository("ProductDao")
public class ProductDaoImpl extends AbstractDao implements ProductDao {

	/*private static final Logger logger = LogManager.getLogger("HelloWorld");*/

	public void saveProduct(Product Product) {
		persist(Product);
	}

	@SuppressWarnings("unchecked")
	public List<Product> findAllProducts(int id) {
		Criteria criteria = getSession().createCriteria(Product.class)
				.add(Restrictions.eq("id",id));
		return (List<Product>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Product> findAllProducts() {
		Criteria criteria = getSession().createCriteria(Product.class);
		return (List<Product>) criteria.list();
	}

	public void deleteProductById(String id) {
		Query query = getSession().createSQLQuery("delete from Product where id = :id");
		query.setString("id", id);
		query.executeUpdate();
	}

	public void deleteProduct(Product Product) {
		getSession().delete(Product);
	}
	
	public void updateProduct(Product Product) {
		getSession().update(Product);
		   
	}

}
