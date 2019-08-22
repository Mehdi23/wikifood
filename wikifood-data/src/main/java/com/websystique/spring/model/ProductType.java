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
@Table(name = "producttype")
public class ProductType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // identifiant
	private String label1;
	private String label2;
	@Column(columnDefinition = "LONGBLOB")
	private byte[] img;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false, updatable = false, insertable = true)
	@JsonBackReference("categoryproducttype")
	private Category category; // Id

	@OneToMany(targetEntity = Product.class, mappedBy = "producttype", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JsonManagedReference("producttypeproduct")
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Product> productlist = new ArrayList<Product>();

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public ProductType() {

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

	/*public Category getCategory() {
		return category;
	}*/

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Product> getProductlist() {
		return productlist;
	}

	public void setProductlist(List<Product> productlist) {
		this.productlist = productlist;
	}

	public void add(Product product) {
		if (product == null) {
			return;
		}
		product.setProducttype(this);
		if (productlist == null) {
			productlist = new ArrayList<Product>();
			productlist.add(product);
		} else if (!productlist.contains(product)) {
			productlist.add(product);
		}
	}

}
