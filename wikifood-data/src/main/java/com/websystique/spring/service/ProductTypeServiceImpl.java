package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.ProductTypeDao;
import com.websystique.spring.model.ProductType;

@Service("ProductTypeService")
@Transactional
public class ProductTypeServiceImpl implements ProductTypeService{

	@Autowired
	private ProductTypeDao dao;
	
	public void saveProductType(ProductType ProductType) {
		dao.saveProductType(ProductType);
	}

	
	public List<ProductType> findProductType(int id) {
		return dao.findProductType(id);
	}
	
	public List<ProductType> findAllProductTypes() {
		return dao.findAllProductTypes();
	}
	
	public void deleteProductType(ProductType ProductType){
		dao.deleteProductType(ProductType);
	}

	public void updateProductType(ProductType ProductType){
		dao.updateProductType(ProductType);
	}

}
