package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.TypeArticle;

public interface TypeArticleDao {

	void saveTypeArticle(TypeArticle TypeArticle);
	
	List<TypeArticle> findAllTypeArticles(TypeArticle TypeArticle);
	
	void deleteTypeArticleById(String id);
	
	void updateTypeArticle(TypeArticle TypeArticle);
}
