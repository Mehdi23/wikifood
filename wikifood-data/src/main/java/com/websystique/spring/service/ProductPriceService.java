package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.ProductPrice;

public interface ProductPriceService {

	void saveProductPrice(ProductPrice ProductPrice);

	List<ProductPrice> findAllProductPrices(int id);
	
	List<ProductPrice> findAllProductPrices();

	void deleteProductPrice(ProductPrice ProductPrice);

	void updateProductPrice(ProductPrice ProductPrice);
}
