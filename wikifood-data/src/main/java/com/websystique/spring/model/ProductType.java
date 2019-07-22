package com.websystique.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "producttype")
public class ProductType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // identifiant
	private String label1;
	private String label2;
	@Column(columnDefinition = "LONGBLOB")
	private byte[] img;
 
	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public ProductType() {

	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCTTYPE_ID", nullable = false)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel1() {
		return label1;
	}

	public void setLabel1(String label1) {
		this.label1 = label1;
	}

	public String getLabel2() {
		return label2;
	}

	public void setLabel2(String label2) {
		this.label2 = label2;
	}
	
}
