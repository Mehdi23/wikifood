package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.Article;

public interface ArticleService {

	void saveArticle(Article article);

	List<Article> findAllArticles(Article article);

	void deleteArticleById(String Id);

	Article findByName(String nom1);

	void updateArticle(Article article);
}
