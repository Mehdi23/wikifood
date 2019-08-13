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
import com.websystique.spring.model.ProductType;
import com.websystique.spring.service.ProductTypeService;

@Path("/producttype")
public class ProductType_ui {
	ProductTypeService service;

	@PostConstruct
	public void init() {
		// ObjectifyService.register(ProductType.class);
	}

	@GET
	@Path("getall")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ProductType> findAll() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (ProductTypeService) context.getBean("ProductTypeService");
			return service.findAllProductTypes();
		} finally {
			context.close();
		}
		
	}

	@POST
	@Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(ProductType p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (ProductTypeService) context.getBean("ProductTypeService");
			service.saveProductType(p);
		} finally {
			context.close();
		}
		
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<ProductType> find(@QueryParam("id") int id) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (ProductTypeService) context.getBean("ProductTypeService");
			return service.findProductType(id);
		
		} finally {
			context.close();
		}
		
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	public void update(ProductType p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (ProductTypeService) context.getBean("ProductTypeService");
			service.updateProductType(p);
		} finally {
			context.close();
		}
		
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	public void remove(ProductType ProductType) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (ProductTypeService) context.getBean("ProductTypeService");
			service.deleteProductType(ProductType);
		} finally {
			context.close();
		}
		

	}

}
