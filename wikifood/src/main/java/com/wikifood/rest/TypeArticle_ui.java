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
import com.websystique.spring.model.TypeArticle;
import com.websystique.spring.service.ArticleService;
import com.websystique.spring.service.TypeArticleService;

@Path("/typearticle")
public class TypeArticle_ui {
	TypeArticleService service;

	@PostConstruct
	public void init() {
		//ObjectifyService.register(TypeArticle.class);
	}
	
	@GET
    @Produces({ MediaType.APPLICATION_JSON })
    public List<TypeArticle> find() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (TypeArticleService) context.getBean("TypeArticleService");
		return service.findAllTypeArticles();
    }
	
	
	@POST @Path("get")
	@Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public List<TypeArticle> findAll(TypeArticle TypeArticle) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (TypeArticleService) context.getBean("TypeArticleService");
		return service.findAllTypeArticles(TypeArticle);
    }

	@POST @Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(TypeArticle p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (TypeArticleService) context.getBean("TypeArticleService");
		service.saveTypeArticle(p);
	}
	
	@PUT
    @Consumes({ MediaType.APPLICATION_JSON})
    public void update(TypeArticle p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (TypeArticleService) context.getBean("TypeArticleService");
		service.updateTypeArticle(p);
    }
 
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON })
    public void remove(TypeArticle typeArticle) {
    	AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (TypeArticleService) context.getBean("TypeArticleService");
    	service.deleteTypeArticle(typeArticle);
    	
    }

}
