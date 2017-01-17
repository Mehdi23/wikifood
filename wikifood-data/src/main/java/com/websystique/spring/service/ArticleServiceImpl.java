package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.ArticleDao;
import com.websystique.spring.model.Article;
import com.websystique.spring.model.TypeArticle;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService{

	@Autowired
	private ArticleDao dao;
	
	public void saveArticle(Article article) {
		dao.saveArticle(article);
	}

	public List<Article> findAllArticles(Article article) {
		return dao.findAllArticles(article);
	}
	
	public List<Article> findAllArticles(String typearticle) {
		return dao.findAllArticles(typearticle);
	}
	
	public List<Article> findAllArticles() {
		return dao.findAllArticles();
	}

	public Article findByName(String nom1) {
		return dao.findByName(nom1);
	}
	
	public void deleteArticle(Article article){
		dao.deleteArticle(article);
	}

	public void updateArticle(Article article){
		dao.updateArticle(article);
	}

}
