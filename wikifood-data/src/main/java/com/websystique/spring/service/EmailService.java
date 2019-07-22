package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.Email;

public interface EmailService {

	void saveEmail(Email Email);

	List<Email> findAllEmails(int id);
	
	List<Email> findAllEmails();

	void deleteEmail(Email Email);

	void updateEmail(Email Email);
}
