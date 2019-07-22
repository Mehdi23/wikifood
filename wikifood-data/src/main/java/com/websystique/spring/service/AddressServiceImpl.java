package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.AddressDao;
import com.websystique.spring.model.Address;

@Service("AddressService")
@Transactional
public class AddressServiceImpl implements AddressService{

	@Autowired
	private AddressDao dao;
	
	public void saveAddress(Address Address) {
		dao.saveAddress(Address);
	}

	
	public List<Address> findAllAddresss(int id) {
		return dao.findAllAddresss(id);
	}
	
	public List<Address> findAllAddresss() {
		return dao.findAllAddresss();
	}
	
	public void deleteAddress(Address Address){
		dao.deleteAddress(Address);
	}

	public void updateAddress(Address Address){
		dao.updateAddress(Address);
	}

}
