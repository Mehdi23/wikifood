package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.Merchant;

public interface MerchantDao {

	void saveMerchant(Merchant Merchant);
	
	List<Merchant> findAllMerchants();
	
	List<Merchant> findMerchant(int id);
	
	void deleteMerchant(Merchant Merchant);
	
	void updateMerchant(Merchant Merchant);
}
