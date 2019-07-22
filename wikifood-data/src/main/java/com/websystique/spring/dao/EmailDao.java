package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.Email;

public interface EmailDao {

	void saveEmail(Email Email);
	
	List<Email> findAllEmails();
	
	List<Email> findAllEmails(int id);
	
	void deleteEmailById(String id);
	
	void deleteEmail(Email Email);
	
	void updateEmail(Email Email);
}
