package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
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
	public List<TypeArticle> findAllTypeArticles() {
		Criteria criteria = getSession().createCriteria(TypeArticle.class);
		return (List<TypeArticle>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<TypeArticle> findAllTypeArticles(TypeArticle TypeArticle) {
		Criteria criteria = getSession().createCriteria(TypeArticle.class)
				.add(Restrictions.like("itemLabelFr", "%" + TypeArticle.getItemLabelFr() + "%"))
				.add(Restrictions.like("itemLabelEn", "%" + TypeArticle.getItemLabelEn() + "%"))
				.add(Restrictions.like("itemLabelAr", "%" + TypeArticle.getItemLabelAr() + "%"));
		return (List<TypeArticle>) criteria.list();
	}

	public void deleteTypeArticle(TypeArticle typeArticle) {
		getSession().delete(typeArticle);
	}
	
	public void updateTypeArticle(TypeArticle typeArticle) {
		getSession().update(typeArticle);
		   
	}
}
