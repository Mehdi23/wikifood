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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.websystique.spring.configuration.AppConfig;
import com.websystique.spring.model.Product;
import com.websystique.spring.service.ProductService;

@Path("/product")
public class Product_ui {
	ProductService service;

	@PostConstruct
	public void init() {
		//ObjectifyService.register(Product.class);
	}

	
	@GET @Path("get")
	@Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Product> findAll() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ProductService) context.getBean("ProductService");
        //context.close();
        return service.findAllProducts();	
    }

	@POST @Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(Product p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ProductService) context.getBean("ProductService");
		service.saveProduct(p);
	}
	
	@GET 
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Product> find(@QueryParam("id")int id) {
		System.out.println("id : " + id);
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ProductService) context.getBean("ProductService");
		return service.findAllProducts(id);
    }
	
	@PUT
    @Consumes({ MediaType.APPLICATION_JSON})
    public void update(Product p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ProductService) context.getBean("ProductService");
		service.updateProduct(p);
    }
 
    @DELETE 
    @Consumes({ MediaType.APPLICATION_JSON })
    public void remove(Product Product) {
    	AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ProductService) context.getBean("ProductService");
    	service.deleteProduct(Product);
    	
    }

}
