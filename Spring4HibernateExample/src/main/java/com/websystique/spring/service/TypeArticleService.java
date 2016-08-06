package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.TypeArticle;

public interface TypeArticleService {

	void saveTypeArticle(TypeArticle TypeArticle);

	List<TypeArticle> findAllTypeArticles(TypeArticle TypeArticle);

	void deleteTypeArticleById(String nom);

	void updateTypeArticle(TypeArticle TypeArticle);
}
