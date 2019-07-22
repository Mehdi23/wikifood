package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.Merchant;

@Repository("MerchantDao")
public class MerchantDaoImpl extends AbstractDao implements MerchantDao {

	/*private static final Logger logger = LogManager.getLogger("HelloWorld");*/

	public void saveMerchant(Merchant Merchant) {
		persist(Merchant);
	}

	@SuppressWarnings("unchecked")
	public List<Merchant> findAllMerchants(int id) {
		Criteria criteria = getSession().createCriteria(Merchant.class)
				.add(Restrictions.eq("id",id));
		return (List<Merchant>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Merchant> findAllMerchants() {
		Criteria criteria = getSession().createCriteria(Merchant.class);
		return (List<Merchant>) criteria.list();
	}

	public void deleteMerchantById(String id) {
		Query query = getSession().createSQLQuery("delete from Merchant where id = :id");
		query.setString("id", id);
		query.executeUpdate();
	}

	public void deleteMerchant(Merchant Merchant) {
		getSession().delete(Merchant);
	}
	
	public void updateMerchant(Merchant Merchant) {
		getSession().update(Merchant);
		   
	}

}
