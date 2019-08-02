package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.Brand;

public interface BrandDao {

	void saveBrand(Brand Brand);
	
	List<Brand> findAllBrands();
	
	List<Brand> findBrand(int id);
	
	void deleteBrand(Brand Brand);
	
	void updateBrand(Brand Brand);
}
