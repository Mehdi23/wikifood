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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String cab; //code a barres du produit
	private String label1; //Libelle du produit en Français
	private String label2; //Libelle du produit en Français
	private String desc1; //Description du produit en Francais
	private String desc2; //Description du produit en Francais

	@Column(columnDefinition = "LONGBLOB")
	private byte[] img; // Image du produit
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id", cascade = CascadeType.ALL)
	private Set<Category> catogory = new HashSet<Category>(0);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id", cascade = CascadeType.ALL)
	private Set<ProductType> productType = new HashSet<ProductType>(0);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id", cascade = CascadeType.ALL)
	private Set<Brand> brand = new HashSet<Brand>(0);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id", cascade = CascadeType.ALL)
	private Set<ProductPrice> productPrice = new HashSet<ProductPrice>(0);

	public Product() {

	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", nullable = false)
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


	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public Set<ProductType> getProductType() {
		return productType;
	}

	public void setProductType(Set<ProductType> productType) {
		this.productType = productType;
	}

	public Set<Brand> getBrand() {
		return brand;
	}

	public void setBrand(Set<Brand> brand) {
		this.brand = brand;
	}

	public Set<ProductPrice> getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Set<ProductPrice> productPrice) {
		this.productPrice = productPrice;
	}

	public Set<Category> getCatogory() {
		return catogory;
	}

	public void setCatogory(Set<Category> catogory) {
		this.catogory = catogory;
	}

}
