package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.ProductType;

@Repository("ProductTypeDao")
public class ProductTypeDaoImpl extends AbstractDao implements ProductTypeDao {

	/*private static final Logger logger = LogManager.getLogger("HelloWorld");*/

	public void saveProductType(ProductType ProductType) {
		persist(ProductType);
	}

	@SuppressWarnings("unchecked")
	public List<ProductType> findAllProductTypes(int id) {
		Criteria criteria = getSession().createCriteria(ProductType.class)
				.add(Restrictions.eq("id",id));
		return (List<ProductType>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductType> findAllProductTypes() {
		Criteria criteria = getSession().createCriteria(ProductType.class);
		return (List<ProductType>) criteria.list();
	}

	public void deleteProductTypeById(String id) {
		Query query = getSession().createSQLQuery("delete from ProductType where id = :id");
		query.setString("id", id);
		query.executeUpdate();
	}

	public void deleteProductType(ProductType ProductType) {
		getSession().delete(ProductType);
	}
	
	public void updateProductType(ProductType ProductType) {
		getSession().update(ProductType);
		   
	}

}
