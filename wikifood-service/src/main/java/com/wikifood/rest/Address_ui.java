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
import com.websystique.spring.model.Address;
import com.websystique.spring.service.AddressService;

@Path("/address")
public class Address_ui {
	AddressService service;

	@PostConstruct
	public void init() {
		// ObjectifyService.register(Address.class);
	}

	@GET
	@Path("get")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Address> findAll() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (AddressService) context.getBean("AddressService");
			return service.findAllAddresss();
		} finally {
			context.close();
		}
		
		
	}

	@POST
	@Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(Address p) {
		
		
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		
		try {
			service = (AddressService) context.getBean("AddressService");
			service.saveAddress(p);
		} finally {
			context.close();
		}		
		
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Address> find(@QueryParam("id") int id) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (AddressService) context.getBean("AddressService");
			return service.findAllAddresss(id);
		} finally {
			context.close();
		}
		
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	public void update(Address p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (AddressService) context.getBean("AddressService");
			service.updateAddress(p);
		} finally {
			context.close();
		}
		
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	public void remove(Address Address) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		try {
			service = (AddressService) context.getBean("AddressService");
			service.deleteAddress(Address);
		} finally {
			context.close();
		}		

	}

}
