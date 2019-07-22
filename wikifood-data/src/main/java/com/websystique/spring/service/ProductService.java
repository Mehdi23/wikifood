package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.Product;

public interface ProductService {

	void saveProduct(Product Product);

	List<Product> findAllProducts(int id);
	
	List<Product> findAllProducts();

	void deleteProduct(Product Product);

	void updateProduct(Product Product);
}
