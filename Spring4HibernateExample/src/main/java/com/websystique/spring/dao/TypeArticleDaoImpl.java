package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.TypeArticle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Repository("TypeArticleDao")
public class TypeArticleDaoImpl extends AbstractDao implements TypeArticleDao {

	private static final Logger logger = LogManager.getLogger("HelloWorld");

	public void saveTypeArticle(TypeArticle TypeArticle) {
		persist(TypeArticle);
	}

	@SuppressWarnings("unchecked")
	public List<TypeArticle> findAllTypeArticles(TypeArticle TypeArticle) {
		Criteria criteria = getSession().createCriteria(TypeArticle.class)
				.add(Restrictions.like("nom", "%" + TypeArticle.getNom() + "%"));
		return (List<TypeArticle>) criteria.list();
	}

	public void deleteTypeArticleById(String nom) {
		Query query = getSession().createSQLQuery("delete from TypeArticle where nom = :nom");
		query.setString("nom", nom);
		query.executeUpdate();
	}

	public void updateTypeArticle(TypeArticle TypeArticle) {
		Query query = getSession().createSQLQuery(
				"update TypeArticle set nom = :nom, description = :description where nom like :nom");
		query.setString("nom", TypeArticle.getNom());
		query.setString("description", "" + TypeArticle.getDescription());
		query.executeUpdate();
	}

}
