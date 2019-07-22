package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.Phone;

@Repository("PhoneDao")
public class PhoneDaoImpl extends AbstractDao implements PhoneDao {

	/*private static final Logger logger = LogManager.getLogger("HelloWorld");*/

	public void savePhone(Phone Phone) {
		persist(Phone);
	}

	@SuppressWarnings("unchecked")
	public List<Phone> findAllPhones(int id) {
		Criteria criteria = getSession().createCriteria(Phone.class)
				.add(Restrictions.eq("id",id));
		return (List<Phone>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Phone> findAllPhones() {
		Criteria criteria = getSession().createCriteria(Phone.class);
		return (List<Phone>) criteria.list();
	}

	public void deletePhoneById(String id) {
		Query query = getSession().createSQLQuery("delete from Phone where id = :id");
		query.setString("id", id);
		query.executeUpdate();
	}

	public void deletePhone(Phone Phone) {
		getSession().delete(Phone);
	}
	
	public void updatePhone(Phone Phone) {
		getSession().update(Phone);
		   
	}

}
