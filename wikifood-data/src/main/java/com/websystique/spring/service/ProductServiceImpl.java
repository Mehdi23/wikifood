package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.ProductDao;
import com.websystique.spring.model.Product;

@Service("ProductService")
@Transactional
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDao dao;
	
	public void saveProduct(Product Product) {
		dao.saveProduct(Product);
	}

	
	public List<Product> findProduct(int id) {
		return dao.findProduct(id);
	}
	
	public List<Product> findAllProducts() {
		return dao.findAllProducts();
	}
	
	public void deleteProduct(Product Product){
		dao.deleteProduct(Product);
	}

	public void updateProduct(Product Product){
		dao.updateProduct(Product);
	}

}
