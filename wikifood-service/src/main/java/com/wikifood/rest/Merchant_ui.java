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
import com.websystique.spring.model.Merchant;
import com.websystique.spring.service.MerchantService;

@Path("/merchant")
public class Merchant_ui {
	MerchantService service;

	@PostConstruct
	public void init() {
		// ObjectifyService.register(Merchant.class);
	}

	@GET
	@Path("getall")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Merchant> findAll() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (MerchantService) context.getBean("MerchantService");
			return service.findAllMerchants();
		} finally {
			context.close();
		}
		
	}

	@POST
	@Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(Merchant p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (MerchantService) context.getBean("MerchantService");
			service.saveMerchant(p);
		} finally {
			context.close();
		}
		
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Merchant find(@QueryParam("id") int id) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (MerchantService) context.getBean("MerchantService");
			return service.findMerchant(id);
		
		} finally {
			context.close();
		}
		
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	public void update(Merchant p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (MerchantService) context.getBean("MerchantService");
			service.updateMerchant(p);
		} finally {
			context.close();
		}
		
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	public void remove(Merchant Merchant) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (MerchantService) context.getBean("MerchantService");
			service.deleteMerchant(Merchant);
		} finally {
			context.close();
		}
		

	}

}
