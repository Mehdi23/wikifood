package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.Brand;

public interface BrandService {

	void saveBrand(Brand Brand);

	List<Brand> findBrand (int id);
	
	List<Brand> findAllBrands();

	void deleteBrand(int id);

	void updateBrand(Brand Brand);
}
