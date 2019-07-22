package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.Product;

public interface ProductDao {

	void saveProduct(Product Product);
	
	List<Product> findAllProducts();
	
	List<Product> findAllProducts(int id);
	
	void deleteProductById(String id);
	
	void deleteProduct(Product Product);
	
	void updateProduct(Product Product);
}
