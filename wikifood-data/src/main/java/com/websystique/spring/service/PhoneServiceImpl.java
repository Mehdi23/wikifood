package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.PhoneDao;
import com.websystique.spring.model.Phone;

@Service("PhoneService")
@Transactional
public class PhoneServiceImpl implements PhoneService{

	@Autowired
	private PhoneDao dao;
	
	public void savePhone(Phone Phone) {
		dao.savePhone(Phone);
	}

	
	public List<Phone> findAllPhones(int id) {
		return dao.findAllPhones(id);
	}
	
	public List<Phone> findAllPhones() {
		return dao.findAllPhones();
	}
	
	public void deletePhone(Phone Phone){
		dao.deletePhone(Phone);
	}

	public void updatePhone(Phone Phone){
		dao.updatePhone(Phone);
	}

}
