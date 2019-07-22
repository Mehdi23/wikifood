package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.Email;

@Repository("EmailDao")
public class EmailDaoImpl extends AbstractDao implements EmailDao {

	/*private static final Logger logger = LogManager.getLogger("HelloWorld");*/

	public void saveEmail(Email Email) {
		persist(Email);
	}

	@SuppressWarnings("unchecked")
	public List<Email> findAllEmails(int id) {
		Criteria criteria = getSession().createCriteria(Email.class)
				.add(Restrictions.eq("id",id));
		return (List<Email>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Email> findAllEmails() {
		Criteria criteria = getSession().createCriteria(Email.class);
		return (List<Email>) criteria.list();
	}

	public void deleteEmailById(String id) {
		Query query = getSession().createSQLQuery("delete from Email where id = :id");
		query.setString("id", id);
		query.executeUpdate();
	}

	public void deleteEmail(Email Email) {
		getSession().delete(Email);
	}
	
	public void updateEmail(Email Email) {
		getSession().update(Email);
		   
	}

}
