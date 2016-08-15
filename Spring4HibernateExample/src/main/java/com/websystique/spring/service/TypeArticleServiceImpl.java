package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.TypeArticleDao;
import com.websystique.spring.model.TypeArticle;

@Service("TypeArticleService")
@Transactional
public class TypeArticleServiceImpl implements TypeArticleService{

	@Autowired
	private TypeArticleDao dao;
	
	public void saveTypeArticle(TypeArticle TypeArticle) {
		dao.saveTypeArticle(TypeArticle);
	}

	public List<TypeArticle> findAllTypeArticles(TypeArticle TypeArticle) {
		return dao.findAllTypeArticles(TypeArticle);
	}
	
	public List<TypeArticle> findAllTypeArticles() {
		return dao.findAllTypeArticles();
	}


	public void deleteTypeArticle(TypeArticle TypeArticle) {
		dao.deleteTypeArticle(TypeArticle);
	}


	public void updateTypeArticle(TypeArticle TypeArticle){
		dao.updateTypeArticle(TypeArticle);
	}
}
