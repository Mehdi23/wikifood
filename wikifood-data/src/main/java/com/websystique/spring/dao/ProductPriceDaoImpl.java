package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.ProductPrice;

@Repository("ProductPriceDao")
public class ProductPriceDaoImpl extends AbstractDao implements ProductPriceDao {

	/*private static final Logger logger = LogManager.getLogger("HelloWorld");*/

	public void saveProductPrice(ProductPrice ProductPrice) {
		persist(ProductPrice);
	}

	@SuppressWarnings("unchecked")
	public List<ProductPrice> findAllProductPrices(int id) {
		Criteria criteria = getSession().createCriteria(ProductPrice.class)
				.add(Restrictions.eq("id",id));
		return (List<ProductPrice>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<ProductPrice> findAllProductPrices() {
		Criteria criteria = getSession().createCriteria(ProductPrice.class);
		return (List<ProductPrice>) criteria.list();
	}

	public void deleteProductPriceById(String id) {
		Query query = getSession().createSQLQuery("delete from ProductPrice where id = :id");
		query.setString("id", id);
		query.executeUpdate();
	}

	public void deleteProductPrice(ProductPrice ProductPrice) {
		getSession().delete(ProductPrice);
	}
	
	public void updateProductPrice(ProductPrice ProductPrice) {
		getSession().update(ProductPrice);
		   
	}

}
