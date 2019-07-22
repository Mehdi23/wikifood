package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.Phone;

public interface PhoneService {

	void savePhone(Phone Phone);

	List<Phone> findAllPhones(int id);
	
	List<Phone> findAllPhones();

	void deletePhone(Phone Phone);

	void updatePhone(Phone Phone);
}
