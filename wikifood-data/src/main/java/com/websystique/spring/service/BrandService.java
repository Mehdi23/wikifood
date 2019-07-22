package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.Brand;

public interface BrandService {

	void saveBrand(Brand Brand);

	List<Brand> findAllBrands(int id);
	
	List<Brand> findAllBrands();

	void deleteBrand(Brand Brand);

	void updateBrand(Brand Brand);
}
