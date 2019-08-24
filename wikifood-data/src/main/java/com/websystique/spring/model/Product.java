package com.websystique.spring.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "product")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String cab; // code a barres du produit
	private String label1; // Libelle du produit en Français
	private String label2; // Libelle du produit en Français
	private String desc1; // Description du produit en Francais
	private String desc2; // Description du produit en Francais
	private Date publication_date; // Date de publication sur le site
	private Date modification_date; // Date de modification
	private Date expiryDate; // Date d'expiration
	private String unit; // Unite de vente (kg, litre, unite, ...)
	private String currency; // Devise de vente
	private float price; // Prix standard de vente
	private boolean available; // Disponibilité en stock
	private int items_number; // Nombre d'articles disponibles en stock
	private boolean promotion; // Disponibilité en promotion
	private float promo_price;// prix de promotion
	private Date promo_expiryDate; // Date d'expiration

	@Column(columnDefinition = "LONGBLOB")
	private byte[] img; // Image du produit

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "merchant_id", nullable = false, updatable = false, insertable = true)
	/*@JsonProperty(value = "merchantid")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)*/
	private Merchant merchant; // Id

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "producttype_id", nullable = true, updatable = false, insertable = true)
	/*@JsonProperty(value = "product_typeid")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)*/
	private ProductType producttype; // Id

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "brand_id", nullable = true, updatable = false, insertable = true)
	/*@JsonProperty(value = "brandid")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)*/
	private Brand brand; // Id

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

	public Date getPublication_date() {
		return publication_date;
	}

	public void setPublication_date(Date publication_date) {
		this.publication_date = publication_date;
	}

	public Date getModification_date() {
		return modification_date;
	}

	public void setModification_date(Date modification_date) {
		this.modification_date = modification_date;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isPromotion() {
		return promotion;
	}

	public void setPromotion(boolean promotion) {
		this.promotion = promotion;
	}

	public float getPromo_price() {
		return promo_price;
	}

	public void setPromo_price(float promo_price) {
		this.promo_price = promo_price;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getItems_number() {
		return items_number;
	}

	public void setItems_number(int items_number) {
		this.items_number = items_number;
	}

	public Date getPromo_expiryDate() {
		return promo_expiryDate;
	}

	public void setPromo_expiryDate(Date promo_expiryDate) {
		this.promo_expiryDate = promo_expiryDate;
	}

	public Merchant getMerchant() {
		return merchant;
	}

	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}

	public ProductType getProducttype() {
		return producttype;
	}

	public void setProducttype(ProductType producttype) {
		this.producttype = producttype;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", label1=" + label1 + "]";
	}

}
