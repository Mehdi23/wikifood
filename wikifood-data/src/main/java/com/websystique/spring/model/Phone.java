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
@Table(name = "phone")
public class Phone {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // Id
	private String type; // type du numero de telephone (mobile/domicile)
	private String number; // numero de telephone
	private int rank;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "merchant_id", nullable = false, updatable = false, insertable = true)
	@JsonBackReference
	private Merchant merchant; // Id

	public Phone() {

	}

	public Phone(int id, String type, String number) {
		super();
		this.type = type;
		this.number = number;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	/*
	 * public Merchant getMerchant() { return merchant; }
	 */

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}
