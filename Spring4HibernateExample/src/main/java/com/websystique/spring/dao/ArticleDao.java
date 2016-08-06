package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.Article;

public interface ArticleDao {

	void saveArticle(Article article);
	
	List<Article> findAllArticles(Article article);
	
	void deleteArticleById(String id);
	
	Article findByName(String nom1);
	
	void updateArticle(Article article);
}
