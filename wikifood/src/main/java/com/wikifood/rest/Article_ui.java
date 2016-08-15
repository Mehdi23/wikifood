package com.wikifood.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.websystique.spring.configuration.AppConfig;
import com.websystique.spring.model.Article;
import com.websystique.spring.service.ArticleService;

@Path("/article")
public class Article_ui {
	ArticleService service;

	@PostConstruct
	public void init() {
		//ObjectifyService.register(Article.class);
	}
	
	@GET
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Article> find() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ArticleService) context.getBean("articleService");
		return service.findAllArticles();
    }
	
	@POST @Path("get")
	@Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Article> findAll(Article article) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ArticleService) context.getBean("articleService");
		return service.findAllArticles(article);
    }

	@POST @Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(Article p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ArticleService) context.getBean("articleService");
		service.saveArticle(p);
	}
	
	@PUT
    @Consumes({ MediaType.APPLICATION_JSON})
    public void update(Article p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ArticleService) context.getBean("articleService");
		service.updateArticle(p);
    }
 
    @DELETE 
    @Consumes({ MediaType.APPLICATION_JSON })
    public void remove(Article article) {
    	AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ArticleService) context.getBean("articleService");
    	service.deleteArticle(article);
    	
    }

}
