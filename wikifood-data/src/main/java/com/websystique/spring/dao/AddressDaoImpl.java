package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.Address;

@Repository("AddressDao")
public class AddressDaoImpl extends AbstractDao implements AddressDao {

	/*private static final Logger logger = LogManager.getLogger("HelloWorld");*/

	public void saveAddress(Address Address) {
		persist(Address);
	}

	@SuppressWarnings("unchecked")
	public List<Address> findAllAddresss(int id) {
		Criteria criteria = getSession().createCriteria(Address.class)
				.add(Restrictions.eq("id",id));
		return (List<Address>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Address> findAllAddresss() {
		Criteria criteria = getSession().createCriteria(Address.class);
		return (List<Address>) criteria.list();
	}

	public void deleteAddressById(String id) {
		Query query = getSession().createSQLQuery("delete from Address where id = :id");
		query.setString("id", id);
		query.executeUpdate();
	}

	public void deleteAddress(Address Address) {
		getSession().delete(Address);
	}
	
	public void updateAddress(Address Address) {
		getSession().update(Address);
		   
	}

}
