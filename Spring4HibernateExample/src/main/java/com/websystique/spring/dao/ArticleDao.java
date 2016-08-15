package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.Article;

public interface ArticleDao {

	void saveArticle(Article article);
	
	List<Article> findAllArticles(Article article);
	
	List<Article> findAllArticles();
	
	void deleteArticle(Article article);
	
	Article findByName(String nom1);
	
	void updateArticle(Article article);
}
