package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.Serveur;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Repository("serveurDao")
public class ServeurDaoImpl extends AbstractDao implements ServeurDao {

	private static final Logger logger = LogManager.getLogger("HelloWorld");

	public void saveServeur(Serveur serveur) {
		persist(serveur);
	}

	@SuppressWarnings("unchecked")
	public List<Serveur> findAllServeurs(Serveur serveur) {
		Criteria criteria = getSession().createCriteria(Serveur.class)
				.add(Restrictions.like("nom", "%" + serveur.getNom() + "%"))
				.add(Restrictions.like("cin", "%" + serveur.getCin() + "%"));
		return (List<Serveur>) criteria.list();
	}

	public void deleteServeurById(String cin) {
		Query query = getSession().createSQLQuery("delete from Serveur where cin = :cin");
		query.setString("cin", cin);
		query.executeUpdate();
	}

	public void updateServeur(Serveur serveur) {
		Query query = getSession().createSQLQuery(
				"update Serveur set nom = :nom, cin = :cin, password = :password where cin like :cin");
		query.setString("nom", serveur.getNom());
		query.setString("cin", "" + serveur.getCin());
		query.setString("password", "" + serveur.getPassword());
		query.executeUpdate();
	}

}
