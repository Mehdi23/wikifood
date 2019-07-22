package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.Merchant;

public interface MerchantService {

	void saveMerchant(Merchant Merchant);

	List<Merchant> findAllMerchants(int id);
	
	List<Merchant> findAllMerchants();

	void deleteMerchant(Merchant Merchant);

	void updateMerchant(Merchant Merchant);
}
