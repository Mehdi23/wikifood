package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.Merchant;

@Repository("MerchantDao")
public class MerchantDaoImpl extends AbstractDao implements MerchantDao {

	/*private static final Logger logger = LogManager.getLogger("HelloWorld");*/
	public void saveMerchant(Merchant Merchant) {
		Merchant.getAddresslist().forEach(address->address.setMerchant(Merchant));
		Merchant.getEmaillist().forEach(email->email.setMerchant(Merchant));
		Merchant.getPhonelist().forEach(phone->phone.setMerchant(Merchant));
		Merchant.getBrandlist().forEach(brand->brand.setMerchant(Merchant));
		Merchant.getCategorylist().forEach(category->category.setMerchant(Merchant));
		Merchant.getProductlist().forEach(product->product.setMerchant(Merchant));
		persist(Merchant);
	}

	public Merchant findMerchant(int id) {
		Criteria criteria= getSession().createCriteria(Merchant.class)
				.add(Restrictions.eq("id",id));
		return (Merchant) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<Merchant> findAllMerchants() {
		Criteria criteria = getSession().createCriteria(Merchant.class);
		return (List<Merchant>) criteria.list();
	}


	public void deleteMerchant(Merchant Merchant) {
		getSession().delete(Merchant);
	}
	
	public void updateMerchant(Merchant Merchant) {
		Merchant.getAddresslist().forEach(address->address.setMerchant(Merchant));
		Merchant.getEmaillist().forEach(email->email.setMerchant(Merchant));
		Merchant.getPhonelist().forEach(phone->phone.setMerchant(Merchant));
		Merchant.getBrandlist().forEach(brand->brand.setMerchant(Merchant));
		Merchant.getCategorylist().forEach(category->category.setMerchant(Merchant));
		Merchant.getProductlist().forEach(product->product.setMerchant(Merchant));
		getSession().update(Merchant);
		   
	}

}
