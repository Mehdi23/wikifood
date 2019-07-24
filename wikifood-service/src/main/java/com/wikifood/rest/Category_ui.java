package com.wikifood.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.websystique.spring.configuration.AppConfig;
import com.websystique.spring.model.Category;
import com.websystique.spring.service.CategoryService;

@Path("/category")
public class Category_ui {
	CategoryService service;

	@PostConstruct
	public void init() {
		// ObjectifyService.register(Category.class);
	}

	@GET
	@Path("get")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Category> findAll() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (CategoryService) context.getBean("CategoryService");
			return service.findAllCategorys();
		} finally {
			context.close();
		}
	}

	@POST
	@Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(Category p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (CategoryService) context.getBean("CategoryService");
			service.saveCategory(p);
		} finally {
			context.close();
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Category> find(@QueryParam("id") int id) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (CategoryService) context.getBean("CategoryService");
			return service.findAllCategorys(id);
		} finally {
			context.close();
		}
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	public void update(Category p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (CategoryService) context.getBean("CategoryService");
			service.updateCategory(p);
		} finally {
			context.close();
		}
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	public void remove(Category Category) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (CategoryService) context.getBean("CategoryService");
			service.deleteCategory(Category);
		} finally {
			context.close();
		}

	}

}
