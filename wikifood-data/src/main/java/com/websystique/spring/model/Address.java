package com.websystique.spring.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // Id
	private String line1; // Ligne 1 de l'adresse
	private String line2; // Ligne 2 de l'adresse
	private String line3; // Ligne 3 de l'adresse
	private String zipcode;
	private String city; // Ville
	private String country; // Country
	private String geoloc; // Localisation geographique
	private int rank;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "merchant_id", nullable = false, updatable = false, insertable = true)
	@JsonBackReference
	private Merchant merchant; // Id

	public Address() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getLine3() {
		return line3;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getGeoloc() {
		return geoloc;
	}

	public void setGeoloc(String geoloc) {
		this.geoloc = geoloc;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	/*
	 * public Merchant getMerchant() { return merchant; }
	 */

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

}
