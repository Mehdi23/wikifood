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
import com.websystique.spring.model.Brand;
import com.websystique.spring.service.BrandService;

@Path("/brand")
public class Brand_ui {
	BrandService service;

	@PostConstruct
	public void init() {
		// ObjectifyService.register(Brand.class);
	}

	@GET
	@Path("getall")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Brand> findAll() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (BrandService) context.getBean("BrandService");
			return service.findAllBrands();
		} finally {
			context.close();
		}
		
	}

	@POST
	@Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(Brand p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (BrandService) context.getBean("BrandService");
			service.saveBrand(p);
		} finally {
			context.close();
		}
		
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Brand> find(@QueryParam("id") int id) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (BrandService) context.getBean("BrandService");
			return service.findBrand(id);
		
		} finally {
			context.close();
		}
		
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	public void update(Brand p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (BrandService) context.getBean("BrandService");
			service.updateBrand(p);
		} finally {
			context.close();
		}
		
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	public void remove(Brand brand) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (BrandService) context.getBean("BrandService");
			service.deleteBrand(brand);
		} finally {
			context.close();
		}
		

	}

}
