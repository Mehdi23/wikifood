package com.websystique.spring.model;

import java.util.ArrayList;
import java.util.List;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // Id de la categorie
	private String label1; // Libelle de la categorie en Francais
	private String label2; // Libelle de la categorie en Arabe

	@Column(columnDefinition = "LONGBLOB")
	private byte[] img; // Logo de la categorie

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "merchant_id", nullable = false, updatable = false, insertable = true)
	@JsonBackReference
	private Merchant merchant; // Id

	@OneToMany(targetEntity = ProductType.class, mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference
	@Fetch(value = FetchMode.SUBSELECT)
	private List<ProductType> productTypelist = new ArrayList<ProductType>();

	public Category() {

	}

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

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	/*
	 * public Merchant getMerchant() { return merchant; }
	 */

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public List<ProductType> getProductTypelist() {
		return productTypelist;
	}

	public void setProductTypelist(List<ProductType> productTypelist) {
		this.productTypelist = productTypelist;
	}

	public void add(ProductType productType) {
		if (productType == null) {
			return;
		}
		productType.setCategory(this);
		if (productTypelist == null) {
			productTypelist = new ArrayList<ProductType>();
			productTypelist.add(productType);
		} else if (!productTypelist.contains(productType)) {
			productTypelist.add(productType);
		}
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", label1=" + label1 + "]";
	}
	
	

}
