package com.JDBC.dao;

import java.util.List;
import java.util.Optional;

import com.JDBC.Exceptions.UsernameTakenException;
import com.JDBC.model.User;

public interface IUserDAO
{
	Optional<User> login(String name, String password);
	
	Optional<List<User>> getAllUsers();
	
	Optional<User> register(String username, String password) throws UsernameTakenException;
	
	void deleteUser(String userName);

	void updateUserPassword(String username, String newPassword);
	
}
