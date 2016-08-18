package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.TypeArticle;

public interface TypeArticleDao {

	void saveTypeArticle(TypeArticle TypeArticle);
	
	List<TypeArticle> findAllTypeArticles();
	
	List<TypeArticle> findAllTypeArticles(TypeArticle TypeArticle);
	
	void deleteTypeArticle(TypeArticle TypeArticle);
	
	void updateTypeArticle(TypeArticle TypeArticle);
}
