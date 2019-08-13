package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.ProductType;

public interface ProductTypeService {

	void saveProductType(ProductType ProductType);

	List<ProductType> findProductType (int id);
	
	List<ProductType> findAllProductTypes();

	void deleteProductType(ProductType ProductType);

	void updateProductType(ProductType ProductType);
}
