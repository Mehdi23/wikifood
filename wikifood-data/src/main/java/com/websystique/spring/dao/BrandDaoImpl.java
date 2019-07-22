package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.Brand;

@Repository("BrandDao")
public class BrandDaoImpl extends AbstractDao implements BrandDao {

	/*private static final Logger logger = LogManager.getLogger("HelloWorld");*/

	public void saveBrand(Brand brand) {
		persist(brand);
	}

	@SuppressWarnings("unchecked")
	public List<Brand> findAllBrands(int id) {
		Criteria criteria = getSession().createCriteria(Brand.class)
				.add(Restrictions.eq("id",id));
		return (List<Brand>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Brand> findAllBrands() {
		Criteria criteria = getSession().createCriteria(Brand.class);
		return (List<Brand>) criteria.list();
	}

	public void deleteBrandById(String id) {
		Query query = getSession().createSQLQuery("delete from Brand where id = :id");
		query.setString("id", id);
		query.executeUpdate();
	}

	public void deleteBrand(Brand Brand) {
		getSession().delete(Brand);
	}
	
	public void updateBrand(Brand Brand) {
		getSession().update(Brand);
		   
	}

}
