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
@Table(name = "merchant")
public class Merchant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;                // Identifiant du commercant
	private String label1;         // Libelle du commercant en Francais
	private String label2;         // Libelle du commercant en Arabe
	private String desc1;          // Description du commercant en Francais
	private String desc2;          // Description du commercant en Arabe
	
	@Column(columnDefinition = "LONGBLOB")
	private byte[] img; // Logo du commerçant
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id", cascade = CascadeType.ALL)
	private Set<Phone> phone = new HashSet<Phone>(0);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id", cascade = CascadeType.ALL)
	private Set<Email> email = new HashSet<Email>(0);
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id", cascade = CascadeType.ALL)
	private Set<Address> address = new HashSet<Address>(0);
     
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "id", cascade = CascadeType.ALL)
	private Set<Product> product = new HashSet<Product>(0);

	public Merchant() {

	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MERCHANT_ID", nullable = false)
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


	public Set<Phone> getPhone() {
		return phone;
	}

	public void setPhone(Set<Phone> phone) {
		this.phone = phone;
	}

	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public Set<Product> getProduct() {
		return product;
	}

	public void setProduct(Set<Product> product) {
		this.product = product;
	}

	public Set<Email> getEmail() {
		return email;
	}

	public void setEmail(Set<Email> email) {
		this.email = email;
	}
	
	
}
