package com.websystique.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "typearticle")
public class TypeArticle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String user;
	private String itemLabelFr;
	private String itemDescFr;
	private String itemLabelEn;
	private String itemDescEn;
	private String itemLabelAr;
	private String itemDescAr;
	@Column(columnDefinition = "LONGBLOB")
	private byte[] img;

	public TypeArticle() {

	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemLabelFr() {
		return itemLabelFr;
	}

	public void setItemLabelFr(String itemLabelFr) {
		this.itemLabelFr = itemLabelFr;
	}

	public String getItemDescFr() {
		return itemDescFr;
	}

	public void setItemDescFr(String itemDescFr) {
		this.itemDescFr = itemDescFr;
	}

	public String getItemLabelEn() {
		return itemLabelEn;
	}

	public void setItemLabelEn(String itemLabelEn) {
		this.itemLabelEn = itemLabelEn;
	}

	public String getItemDescEn() {
		return itemDescEn;
	}

	public void setItemDescEn(String itemDescEn) {
		this.itemDescEn = itemDescEn;
	}

	public String getItemLabelAr() {
		return itemLabelAr;
	}

	public void setItemLabelAr(String itemLabelAr) {
		this.itemLabelAr = itemLabelAr;
	}

	public String getItemDescAr() {
		return itemDescAr;
	}

	public void setItemDescAr(String itemDescAr) {
		this.itemDescAr = itemDescAr;
	}
	
	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

}
