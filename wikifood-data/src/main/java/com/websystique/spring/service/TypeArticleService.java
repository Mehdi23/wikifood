package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.TypeArticle;

public interface TypeArticleService {

	void saveTypeArticle(TypeArticle TypeArticle);

	List<TypeArticle> findAllTypeArticles(TypeArticle TypeArticle);
	
	List<TypeArticle> findAllTypeArticles();

	void deleteTypeArticle(TypeArticle typeArticle);

	void updateTypeArticle(TypeArticle TypeArticle);
}
