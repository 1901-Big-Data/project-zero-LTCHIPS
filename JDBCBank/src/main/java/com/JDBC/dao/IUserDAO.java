package com.JDBC.dao;

import java.util.List;
import java.util.Optional;

import com.JDBC.model.User;

public interface IUserDAO
{
	Optional<User> getUserByName(String name);
	
	Optional<List<User>> getAllUsers();
	
	void saveUser(User user);
	
	void updateUser(User User, String [] params);
	
	void deleteUser(User User);
	
}
