package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.Article;
import com.websystique.spring.model.TypeArticle;

import org.apache.commons.logging.impl.ServletContextCleaner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Repository("articleDao")
public class ArticleDaoImpl extends AbstractDao implements ArticleDao {

	private static final Logger logger = LogManager.getLogger("HelloWorld");

	public void saveArticle(Article article) {
		persist(article);
	}

	@SuppressWarnings("unchecked")
	public List<Article> findAllArticles(Article article) {
		//System.out.println("Fr : " + article.getItemLabelEn() + " En : " + article.getItemLabelFr());
		Criteria criteria = getSession().createCriteria(Article.class)
				.add(Restrictions.like("itemLabelFr", "%" + article.getItemLabelFr() + "%"))
				.add(Restrictions.like("itemLabelEn", "%" + article.getItemLabelEn() + "%"))
				.add(Restrictions.like("itemLabelAr", "%" + article.getItemLabelAr() + "%"));
		return (List<Article>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> findAllArticles(String typearticle) {
		Criteria criteria = getSession().createCriteria(Article.class);
		criteria.add(Restrictions.eq("type", typearticle));
		return (List<Article>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> findAllArticles() {
		//System.out.println("Fr : " + article.getItemLabelEn() + " En : " + article.getItemLabelFr());
		Criteria criteria = getSession().createCriteria(Article.class);
		return (List<Article>) criteria.list();
	}

	public void deleteArticle(Article article) {
		getSession().delete(article);
	}
	
	public void updateArticle(Article article) {
		getSession().update(article);
		   
	}

	public Article findByName(String item) {
		Criteria criteria = getSession().createCriteria(Article.class);
		criteria.add(Restrictions.eq("item", item));
		return (Article) criteria.uniqueResult();
	}

}
