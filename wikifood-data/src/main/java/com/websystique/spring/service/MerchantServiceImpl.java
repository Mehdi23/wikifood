package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.MerchantDao;
import com.websystique.spring.model.Merchant;

@Service("MerchantService")
@Transactional
public class MerchantServiceImpl implements MerchantService{

	@Autowired
	private MerchantDao dao;
	
	public void saveMerchant(Merchant Merchant) {
		dao.saveMerchant(Merchant);
	}

	
	public List<Merchant> findMerchant(int id) {
		return dao.findMerchant(id);
	}
	
	public List<Merchant> findAllMerchants() {
		return dao.findAllMerchants();
	}
	
	public void deleteMerchant(Merchant Merchant){
		dao.deleteMerchant(Merchant);
	}

	public void updateMerchant(Merchant Merchant){
		dao.updateMerchant(Merchant);
	}

}
