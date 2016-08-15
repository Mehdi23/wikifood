package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.ServeurDao;
import com.websystique.spring.model.Serveur;

@Service("ServeurService")
@Transactional
public class ServeurServiceImpl implements ServeurService{

	@Autowired
	private ServeurDao dao;
	
	public void saveServeur(Serveur Serveur) {
		dao.saveServeur(Serveur);
	}

	public List<Serveur> findAllServeurs(Serveur Serveur) {
		return dao.findAllServeurs(Serveur);
	}

	public void deleteServeurById(String id) {
		dao.deleteServeurById(id);
	}
	
	public void deleteServeur(Serveur serveur) {
		dao.deleteServeur(serveur);
	}


	public void updateServeur(Serveur Serveur){
		dao.updateServeur(Serveur);
	}
}
