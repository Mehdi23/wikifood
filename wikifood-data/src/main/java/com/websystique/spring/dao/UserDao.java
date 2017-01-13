package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.Serveur;

public interface ServeurDao {

	void saveServeur(Serveur serveur);
	
	List<Serveur> findAllServeurs(Serveur serveur);
	
	void deleteServeurById(String id);
	
	void deleteServeur(Serveur serveur);
	
	void updateServeur(Serveur serveur);
}
