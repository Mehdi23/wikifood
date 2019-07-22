package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.Address;

public interface AddressDao {

	void saveAddress(Address Address);
	
	List<Address> findAllAddresss();
	
	List<Address> findAllAddresss(int id);
	
	void deleteAddressById(String id);
	
	void deleteAddress(Address Address);
	
	void updateAddress(Address Address);
}
