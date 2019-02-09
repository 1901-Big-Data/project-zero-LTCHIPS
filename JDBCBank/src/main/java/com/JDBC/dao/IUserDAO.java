package com.JDBC.dao;

import java.util.List;
import java.util.Optional;

import com.JDBC.model.User;

public interface IUserDAO
{
	Optional<User> getUserByName(String name);
	
	Optional<User> login(String name, String password);
	
	Optional<List<User>> getAllUsers();
	
	Optional<User> register(String username, String password);
	
		
		
	
	
	void updateUser(User User, String [] params);
	
	void deleteUser(User User);
	
}
