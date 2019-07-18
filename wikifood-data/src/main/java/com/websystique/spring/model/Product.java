package com.websystique.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String cab; //code à barres
	private String label1;
	private String label2;
	private String desc1;
	private String desc2;

	@Column(columnDefinition = "LONGBLOB")
	private byte[] img;
	
	
	@ManyToOne
    @JoinColumn(name="producttype_id", nullable=false)
    private Category productType;

	public Product() {

	}
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}



	public String getCab() {
		return cab;
	}



	public void setCab(String cab) {
		this.cab = cab;
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



	public String getDesc1() {
		return desc1;
	}



	public void setDesc1(String desc1) {
		this.desc1 = desc1;
	}

	public String getDesc2() {
		return desc2;
	}

	public void setDesc2(String desc2) {
		this.desc2 = desc2;
	}


	public Category getProductType() {
		return productType;
	}



	public void setProductType(Category productType) {
		this.productType = productType;
	}



	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}


}
