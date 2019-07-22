package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.ProductPrice;

public interface ProductPriceDao {

	void saveProductPrice(ProductPrice ProductPrice);
	
	List<ProductPrice> findAllProductPrices();
	
	List<ProductPrice> findAllProductPrices(int id);
	
	void deleteProductPriceById(String id);
	
	void deleteProductPrice(ProductPrice ProductPrice);
	
	void updateProductPrice(ProductPrice ProductPrice);
}
