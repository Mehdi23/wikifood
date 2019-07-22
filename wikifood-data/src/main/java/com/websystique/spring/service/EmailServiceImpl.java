package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.EmailDao;
import com.websystique.spring.model.Email;

@Service("EmailService")
@Transactional
public class EmailServiceImpl implements EmailService{

	@Autowired
	private EmailDao dao;
	
	public void saveEmail(Email Email) {
		dao.saveEmail(Email);
	}

	
	public List<Email> findAllEmails(int id) {
		return dao.findAllEmails(id);
	}
	
	public List<Email> findAllEmails() {
		return dao.findAllEmails();
	}
	
	public void deleteEmail(Email Email){
		dao.deleteEmail(Email);
	}

	public void updateEmail(Email Email){
		dao.updateEmail(Email);
	}

}
