package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.Merchant;

public interface MerchantDao {

	void saveMerchant(Merchant Merchant);
	
	List<Merchant> findAllMerchants();
	
	List<Merchant> findAllMerchants(int id);
	
	void deleteMerchantById(String id);
	
	void deleteMerchant(Merchant Merchant);
	
	void updateMerchant(Merchant Merchant);
}
