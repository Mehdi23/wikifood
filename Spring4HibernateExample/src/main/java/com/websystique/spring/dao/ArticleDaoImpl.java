package com.websystique.spring.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.websystique.spring.model.Article;
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
				.add(Restrictions.like("item", "%" + article.getItem() + "%"))
				.add(Restrictions.like("itemLabelFr", "%" + article.getItemLabelFr() + "%"))
				.add(Restrictions.like("itemLabelEn", "%" + article.getItemLabelEn() + "%"))
				.add(Restrictions.like("itemLabelEs", "%" + article.getItemLabelEs() + "%"))
				.add(Restrictions.like("itemLabelAr", "%" + article.getItemLabelAr() + "%"));
		return (List<Article>) criteria.list();
	}

	public void deleteArticleById(String item) {
		Query query = getSession().createSQLQuery("delete from Article where item = :item");
		query.setString("item", item);
		query.executeUpdate();
	}

	public Article findByName(String item) {
		Criteria criteria = getSession().createCriteria(Article.class);
		criteria.add(Restrictions.eq("item", item));
		return (Article) criteria.uniqueResult();
	}

	public void updateArticle(Article article) {
		// getSession().update(article);
		Query query = getSession().createSQLQuery(
				"update Article set itemLabelEn = :itemLabelEn, itemLabelEs = :itemLabelEs, itemLabelFr = :itemLabelFr, itemLabelAr = :itemLabelAr where item like :item");
		query.setString("item", article.getItem());
		query.setString("itemLabelEn", "" + article.getItemLabelEn());
		query.setString("itemLabelEs", "" + article.getItemLabelEs());
		query.setString("itemLabelFr", "" + article.getItemLabelFr());
		query.setString("itemLabelAr", "" + article.getItemLabelAr());
		query.executeUpdate();
	}

}
