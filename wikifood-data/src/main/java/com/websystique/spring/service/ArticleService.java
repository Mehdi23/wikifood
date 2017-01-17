package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.Article;
import com.websystique.spring.model.TypeArticle;

public interface ArticleService {

	void saveArticle(Article article);

	List<Article> findAllArticles(Article article);
	
	List<Article> findAllArticles(String typeArticle);
	
	List<Article> findAllArticles();

	void deleteArticle(Article article);

	Article findByName(String nom1);

	void updateArticle(Article article);
}
