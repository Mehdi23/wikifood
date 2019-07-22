package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.Phone;

public interface PhoneDao {

	void savePhone(Phone Phone);
	
	List<Phone> findAllPhones();
	
	List<Phone> findAllPhones(int id);
	
	void deletePhoneById(String id);
	
	void deletePhone(Phone Phone);
	
	void updatePhone(Phone Phone);
}
