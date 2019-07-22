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
import com.websystique.spring.model.Merchant;
import com.websystique.spring.service.MerchantService;

@Path("/merchant")
public class Merchant_ui {
	MerchantService service;

	@PostConstruct
	public void init() {
		//ObjectifyService.register(Merchant.class);
	}

	
	@GET @Path("get")
	@Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Merchant> findAll() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (MerchantService) context.getBean("MerchantService");
        //context.close();
        return service.findAllMerchants();	
    }

	@POST @Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(Merchant p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (MerchantService) context.getBean("MerchantService");
		service.saveMerchant(p);
	}
	
	@GET 
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Merchant> find(@QueryParam("id")int id) {
		System.out.println("id : " + id);
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (MerchantService) context.getBean("MerchantService");
		return service.findAllMerchants(id);
    }
	
	@PUT
    @Consumes({ MediaType.APPLICATION_JSON})
    public void update(Merchant p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (MerchantService) context.getBean("MerchantService");
		service.updateMerchant(p);
    }
 
    @DELETE 
    @Consumes({ MediaType.APPLICATION_JSON })
    public void remove(Merchant Merchant) {
    	AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (MerchantService) context.getBean("MerchantService");
    	service.deleteMerchant(Merchant);
    	
    }

}
