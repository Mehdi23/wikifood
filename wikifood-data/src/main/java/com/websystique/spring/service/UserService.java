package com.websystique.spring.service;

import java.util.List;

import com.websystique.spring.model.User;

public interface UserService {

	void saveUser(User User);

	List<User> findAllUsers(User User);

	void deleteUserById(String cin);
	
	void deleteUser(User User);

	void updateUser(User User);
}
