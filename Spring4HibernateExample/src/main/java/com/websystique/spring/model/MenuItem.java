package com.websystique.spring.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MenuItem")
public class MenuItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int article;
	private String articlelabel;
	private int typearticle;
	private String typearticlelabel;
	private int prix;
	private boolean disponible;

	public MenuItem() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArticle() {
		return article;
	}

	public void setArticle(int article) {
		this.article = article;
	}

	public int getTypearticle() {
		return typearticle;
	}

	public void setTypearticle(int typearticle) {
		this.typearticle = typearticle;
	}

	
	public String getArticlelabel() {
		return articlelabel;
	}

	public void setArticlelabel(String articlelabel) {
		this.articlelabel = articlelabel;
	}

	public String getTypearticlelabel() {
		return typearticlelabel;
	}

	public void setTypearticlelabel(String typearticlelabel) {
		this.typearticlelabel = typearticlelabel;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}

	public boolean isDisponible() {
		return disponible;
	}

	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

}
