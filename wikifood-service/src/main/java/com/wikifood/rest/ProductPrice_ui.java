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
import com.websystique.spring.model.ProductPrice;
import com.websystique.spring.service.ProductPriceService;

@Path("/productprice")
public class ProductPrice_ui {
	ProductPriceService service;

	@PostConstruct
	public void init() {
		//ObjectifyService.register(ProductPrice.class);
	}

	
	@GET @Path("get")
	@Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public List<ProductPrice> findAll() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ProductPriceService) context.getBean("ProductPriceService");
        //context.close();
        return service.findAllProductPrices();	
    }

	@POST @Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(ProductPrice p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ProductPriceService) context.getBean("ProductPriceService");
		service.saveProductPrice(p);
	}
	
	@GET 
    @Produces({ MediaType.APPLICATION_JSON })
    public List<ProductPrice> find(@QueryParam("id")int id) {
		System.out.println("id : " + id);
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ProductPriceService) context.getBean("ProductPriceService");
		return service.findAllProductPrices(id);
    }
	
	@PUT
    @Consumes({ MediaType.APPLICATION_JSON})
    public void update(ProductPrice p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ProductPriceService) context.getBean("ProductPriceService");
		service.updateProductPrice(p);
    }
 
    @DELETE 
    @Consumes({ MediaType.APPLICATION_JSON })
    public void remove(ProductPrice ProductPrice) {
    	AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ProductPriceService) context.getBean("ProductPriceService");
    	service.deleteProductPrice(ProductPrice);
    	
    }

}
