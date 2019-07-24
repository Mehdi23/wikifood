package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.BrandDao;
import com.websystique.spring.model.Brand;

@Service("BrandService")
@Transactional
public class BrandServiceImpl implements BrandService{

	@Autowired
	private BrandDao dao;
	
	public void saveBrand(Brand Brand) {
		dao.saveBrand(Brand);
	}

	
	public List<Brand> findBrand(int id) {
		return dao.findBrand(id);
	}
	
	public List<Brand> findAllBrands() {
		return dao.findAllBrands();
	}
	
	public void deleteBrand(int id){
		dao.deleteBrand(id);
	}

	public void updateBrand(Brand Brand){
		dao.updateBrand(Brand);
	}

}
