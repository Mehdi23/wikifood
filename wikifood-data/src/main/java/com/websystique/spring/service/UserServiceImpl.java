package com.websystique.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.spring.dao.UserDao;
import com.websystique.spring.model.User;

@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;
	
	public void saveUser(User User) {
		dao.saveUser(User);
	}

	public List<User> findAllUsers(User User) {
		return dao.findAllUsers(User);
	}

	public void deleteUserById(String id) {
		dao.deleteUserById(id);
	}
	
	public void deleteUser(User User) {
		dao.deleteUser(User);
	}


	public void updateUser(User User){
		dao.updateUser(User);
	}
}
