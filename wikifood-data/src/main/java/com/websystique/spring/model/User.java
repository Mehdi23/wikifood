package com.websystique.spring.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; //Identifiant
	private String nom; // Nom de l'utilisateur
	private String login; //Login de l'utilisateur
	private String password; //Mot de passe de l'utilisateur
	private String description; //Description de l'utilisateur
	private String lastconnect; //Date heure de derniere connexion
	private boolean active; //Utilisateur active/desactive
	
	@Column(columnDefinition = "LONGBLOB")
	private byte[] img;
  
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id", cascade = CascadeType.ALL)
	private Set<Merchant> merchant = new HashSet<Merchant>(0);
	
 	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public User() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLastconnect() {
		return lastconnect;
	}

	public void setLastconnect(String lastconnect) {
		this.lastconnect = lastconnect;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
}
