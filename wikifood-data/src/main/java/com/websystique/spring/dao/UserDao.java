package com.websystique.spring.dao;

import java.util.List;

import com.websystique.spring.model.User;

public interface UserDao {

	void saveUser(User User);
	
	List<User> findAllUsers(User User);
	
	void deleteUserById(String id);
	
	void deleteUser(User User);
	
	void updateUser(User User);
}
