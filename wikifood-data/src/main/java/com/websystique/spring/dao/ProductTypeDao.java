package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.ProductType;

public interface ProductTypeDao {

	void saveProductType(ProductType ProductType);
	
	List<ProductType> findAllProductTypes();
	
	List<ProductType> findAllProductTypes(int id);
	
	void deleteProductTypeById(String id);
	
	void deleteProductType(ProductType ProductType);
	
	void updateProductType(ProductType ProductType);
}
