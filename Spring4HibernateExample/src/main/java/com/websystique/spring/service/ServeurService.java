package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.Serveur;

public interface ServeurService {

	void saveServeur(Serveur Serveur);

	List<Serveur> findAllServeurs(Serveur Serveur);

	void deleteServeurById(String cin);

	void updateServeur(Serveur Serveur);
}
