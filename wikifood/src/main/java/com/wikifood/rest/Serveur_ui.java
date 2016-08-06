package com.wikifood.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.websystique.spring.configuration.AppConfig;
import com.websystique.spring.model.Serveur;
import com.websystique.spring.service.ServeurService;

@Path("/serveur")
public class Serveur_ui {
	ServeurService service;

	@PostConstruct
	public void init() {
		//ObjectifyService.register(Serveur.class);
	}
	
	@POST @Path("get")
	@Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public List<Serveur> findAll(Serveur Serveur) {
		System.out.println("Serveur.nom"+Serveur.getNom());
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ServeurService) context.getBean("ServeurService");
		return service.findAllServeurs(Serveur);
    }

	@POST @Path("save")
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(Serveur p) {
		System.out.println("p.nom"+p.getNom());
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ServeurService) context.getBean("ServeurService");
		service.saveServeur(p);
	}
	
	@PUT
    @Consumes({ MediaType.APPLICATION_JSON})
    public void update(Serveur p) {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ServeurService) context.getBean("ServeurService");
		service.updateServeur(p);
    }
 
    @DELETE @Path("{id}")
    @Consumes({ MediaType.APPLICATION_JSON })
    public void remove(@PathParam("id") String id) {
    	AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = (ServeurService) context.getBean("ServeurService");
    	service.deleteServeurById(id);
    	
    }

}
