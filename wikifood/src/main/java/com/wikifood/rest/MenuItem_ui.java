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
import com.websystique.spring.model.MenuItem;
import com.websystique.spring.service.MenuItemService;

@Path("/menuitem")
public class MenuItem_ui {
	MenuItemService service;

	@PostConstruct
	public void init() {
		//ObjectifyService.register(MenuItem.class);
	}
	
	@GET
    @Produces({ MediaType.APPLICATION_JSON })
    public List<MenuItem> find() {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (MenuItemService) context.getBean("MenuItemService");
		return service.findAllMenuItems();
    }
	
	@POST @Path("get")
	@Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public List<MenuItem> findAll(MenuItem MenuItem) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (MenuItemService) context.getBean("MenuItemService");
		return service.findAllMenuItems(MenuItem);
    }

	@POST @Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(MenuItem p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (MenuItemService) context.getBean("MenuItemService");
		service.saveMenuItem(p);
	}
	
	@PUT
    @Consumes({ MediaType.APPLICATION_JSON})
    public void update(MenuItem p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (MenuItemService) context.getBean("MenuItemService");
		service.updateMenuItem(p);
    }
 
    @DELETE 
    @Consumes({ MediaType.APPLICATION_JSON })
    public void remove(MenuItem MenuItem) {
    	AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (MenuItemService) context.getBean("MenuItemService");
    	service.deleteMenuItem(MenuItem);
    	
    }

}
