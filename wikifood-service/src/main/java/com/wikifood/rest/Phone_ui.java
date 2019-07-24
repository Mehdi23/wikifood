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
import com.websystique.spring.model.Phone;
import com.websystique.spring.service.PhoneService;

@Path("/phone")
public class Phone_ui {
	PhoneService service;

	@PostConstruct
	public void init() {
		// ObjectifyService.register(Phone.class);
	}

	@GET
	@Path("get")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Phone> findAll() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (PhoneService) context.getBean("PhoneService");
			return service.findAllPhones();

		} finally {
			context.close();
		}
	}

	@POST
	@Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(Phone p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (PhoneService) context.getBean("PhoneService");
			service.savePhone(p);

		} finally {
			context.close();
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Phone> find(@QueryParam("id") int id) {

		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (PhoneService) context.getBean("PhoneService");
			return service.findAllPhones(id);

		} finally {
			context.close();
		}
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	public void update(Phone p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (PhoneService) context.getBean("PhoneService");
			service.updatePhone(p);

		} finally {
			context.close();
		}
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	public void remove(Phone Phone) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (PhoneService) context.getBean("PhoneService");
			service.deletePhone(Phone);

		} finally {
			context.close();
		}

	}

}
