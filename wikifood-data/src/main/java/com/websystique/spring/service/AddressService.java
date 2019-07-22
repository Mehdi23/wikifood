package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.Address;

public interface AddressService {

	void saveAddress(Address Address);

	List<Address> findAllAddresss(int id);
	
	List<Address> findAllAddresss();

	void deleteAddress(Address Address);

	void updateAddress(Address Address);
}
