package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.ProductPriceDao;
import com.websystique.spring.model.ProductPrice;

@Service("ProductPriceService")
@Transactional
public class ProductPriceServiceImpl implements ProductPriceService{

	@Autowired
	private ProductPriceDao dao;
	
	public void saveProductPrice(ProductPrice ProductPrice) {
		dao.saveProductPrice(ProductPrice);
	}

	
	public List<ProductPrice> findAllProductPrices(int id) {
		return dao.findAllProductPrices(id);
	}
	
	public List<ProductPrice> findAllProductPrices() {
		return dao.findAllProductPrices();
	}
	
	public void deleteProductPrice(ProductPrice ProductPrice){
		dao.deleteProductPrice(ProductPrice);
	}

	public void updateProductPrice(ProductPrice ProductPrice){
		dao.updateProductPrice(ProductPrice);
	}

}
