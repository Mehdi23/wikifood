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
import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.websystique.spring.configuration.AppConfig;
import com.websystique.spring.model.User;
import com.websystique.spring.service.UserService;

@Path("/user")
public class User_ui {
	UserService service;

	@PostConstruct
	public void init() {
		// ObjectifyService.register(User.class);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> find() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (UserService) context.getBean("UserService");
			return service.findAllUsers();
		} finally {
			context.close();
		}
	}

	@GET
	@Path("get")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public List<User> findAll(User User) {
		System.out.println("User.nom" + User.getNom());
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (UserService) context.getBean("UserService");
			return service.findAllUsers(User);
		} finally {
			context.close();
		}
	}

	@POST
	@Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(User p) {
		System.out.println("p.nom" + p.getNom());
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (UserService) context.getBean("UserService");
			service.saveUser(p);
		} finally {
			context.close();
		}
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	public void update(User p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (UserService) context.getBean("UserService");
			service.updateUser(p);
		} finally {
			context.close();
		}
	}

	@DELETE
	@Consumes({ MediaType.APPLICATION_JSON })
	public void remove(User User) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		try {
			service = (UserService) context.getBean("UserService");
			service.deleteUser(User);
		} finally {
			context.close();
		}

	}

}
