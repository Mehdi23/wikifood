package com.websystique.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Article")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String item;
	private String itemLabelFr;
	private String itemLabelEn;
	private String itemLabelEs;
	private String itemLabelAr;
	@Column(columnDefinition = "LONGBLOB")
	private byte[] img;

	public Article() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItemLabelFr() {
		return itemLabelFr;
	}

	public void setItemLabelFr(String itemLabelFr) {
		this.itemLabelFr = itemLabelFr;
	}

	public String getItemLabelEn() {
		return itemLabelEn;
	}

	public void setItemLabelEn(String itemLabelEn) {
		this.itemLabelEn = itemLabelEn;
	}

	public String getItemLabelEs() {
		return itemLabelEs;
	}

	public void setItemLabelEs(String itemLabelEs) {
		this.itemLabelEs = itemLabelEs;
	}

	public String getItemLabelAr() {
		return itemLabelAr;
	}

	public void setItemLabelAr(String itemLabelAr) {
		this.itemLabelAr = itemLabelAr;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}


}
