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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * @author malaoui
 *
 */
@Entity
@Table(name = "merchant")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Merchant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // Identifiant du commercant
	private String label1; // Libelle du commercant en Francais
	private String label2; // Libelle du commercant en Arabe
	private String desc1; // Description du commercant en Francais
	private String desc2; // Description du commercant en Arabe

	@Column(columnDefinition = "LONGBLOB")
	private byte[] img; // Logo du commerçant

	@OneToMany(targetEntity = Address.class, mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Address> addresslist = new ArrayList<Address>();

	@OneToMany(targetEntity = Phone.class, mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Phone> phonelist = new ArrayList<Phone>();

	@OneToMany(targetEntity = Email.class, mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Email> emaillist = new ArrayList<Email>();

	@OneToMany(targetEntity = Brand.class, mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Brand> brandlist = new ArrayList<Brand>();

	@OneToMany(targetEntity = Category.class, mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Category> categorylist = new ArrayList<Category>();
	
	@OneToMany(targetEntity = Product.class, mappedBy = "merchant", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Product> productlist = new ArrayList<Product>();

	public Merchant() {

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

	public List<Address> getAddresslist() {
		return addresslist;
	}

	public void setAddresslist(List<Address> addresslist) {
		this.addresslist = addresslist;
	}

	public List<Phone> getPhonelist() {
		return phonelist;
	}

	public void setPhonelist(List<Phone> phonelist) {
		this.phonelist = phonelist;
	}

	public List<Email> getEmaillist() {
		return emaillist;
	}

	public void setEmaillist(List<Email> emaillist) {
		this.emaillist = emaillist;
	}

	public List<Brand> getBrandlist() {
		return brandlist;
	}

	public void setBrandlist(List<Brand> brandlist) {
		this.brandlist = brandlist;
	}

	public List<Category> getCategorylist() {
		return categorylist;
	}

	public void setCategorylist(List<Category> categorylist) {
		this.categorylist = categorylist;
	}

	public List<Product> getProductlist() {
		return productlist;
	}

	public void setProductlist(List<Product> productlist) {
		this.productlist = productlist;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public void add(Address address) {
		if (address == null) {
			return;
		}
		address.setMerchant(this);
		if (addresslist == null) {
			addresslist = new ArrayList<Address>();
			addresslist.add(address);
		} else if (!addresslist.contains(address)) {
			addresslist.add(address);
		}
	}

	public void add(Phone phone) {
		if (phone == null) {
			return;
		}
		phone.setMerchant(this);
		if (phonelist == null) {
			phonelist = new ArrayList<Phone>();
			phonelist.add(phone);
		} else if (!phonelist.contains(phone)) {
			phonelist.add(phone);
		}
	}

	public void add(Email email) {
		if (email == null) {
			return;
		}
		email.setMerchant(this);
		if (emaillist == null) {
			emaillist = new ArrayList<Email>();
			emaillist.add(email);
		} else if (!emaillist.contains(email)) {
			emaillist.add(email);
		}
	}

	public void add(Brand brand) {
		if (brand == null) {
			return;
		}
		brand.setMerchant(this);
		if (brandlist == null) {
			brandlist = new ArrayList<Brand>();
			brandlist.add(brand);
		} else if (!brandlist.contains(brand)) {
			brandlist.add(brand);
		}
	}
	
	public void add(Category category) {
		if (category == null) {
			return;
		}
		category.setMerchant(this);
		if (categorylist == null) {
			categorylist = new ArrayList<Category>();
			categorylist.add(category);
		} else if (!categorylist.contains(category)) {
			categorylist.add(category);
		}
	}

	public void add(Product product) {
		if (product == null) {
			return;
		}
		product.setMerchant(this);
		if (productlist == null) {
			productlist = new ArrayList<Product>();
			productlist.add(product);
		} else if (!productlist.contains(product)) {
			productlist.add(product);
		}
	}
	
	@Override
	public String toString() {
		return "Merchant [id=" + id + ", label1=" + label1 + "]";
	}
	
	

}
