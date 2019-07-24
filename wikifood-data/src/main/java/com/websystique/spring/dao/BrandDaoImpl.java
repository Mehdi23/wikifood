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
	public List<Brand> findBrand(int id) {
		Criteria criteria = getSession().createCriteria(Brand.class)
				.add(Restrictions.eq("id",id));
		return (List<Brand>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Brand> findAllBrands() {
		Criteria criteria = getSession().createCriteria(Brand.class);
		return (List<Brand>) criteria.list();
	}


	public void deleteBrand(int id) {
		Query query = getSession().createSQLQuery("delete from Brand where id = :id");
		query.setLong("id", id);
		query.executeUpdate();
	}
	
	public void updateBrand(Brand Brand) {
		getSession().update(Brand);
		   
	}

}
