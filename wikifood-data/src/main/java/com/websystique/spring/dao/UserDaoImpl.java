package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Repository("UserDao")
public class UserDaoImpl extends AbstractDao implements UserDao {

	private static final Logger logger = LogManager.getLogger("HelloWorld");

	public void saveUser(User User) {
		persist(User);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers(User User) {
		Criteria criteria = getSession().createCriteria(User.class)
				.add(Restrictions.like("nom", "%" + User.getNom() + "%"))
				.add(Restrictions.like("login", "%" + User.getLogin() + "%"));
		return (List<User>) criteria.list();
	}
	
	public List<User> findAllUsers() {
		Criteria criteria = getSession().createCriteria(User.class)
				.add(Restrictions.like("nom", "%%"));
				//.add(Restrictions.like("nom", "%" + User.getNom() + "%"));
		return (List<User>) criteria.list();
	}

	public void deleteUserById(String cin) {
		Query query = getSession().createSQLQuery("delete from User where cin = :cin");
		query.setString("cin", cin);
		query.executeUpdate();
	}

	public void deleteUser(User User) {
		getSession().delete(User);
	}
	
	public void updateUser(User User) {
		getSession().update(User);
		   
	}

}
