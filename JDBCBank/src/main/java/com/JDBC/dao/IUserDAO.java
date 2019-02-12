package com.JDBC.dao;

import java.util.List;
import java.util.Optional;

import com.JDBC.Exceptions.IncorrectLoginException;
import com.JDBC.Exceptions.UsernameTakenException;
import com.JDBC.model.User;

public interface IUserDAO
{
	Optional<User> login(String name, String password) throws IncorrectLoginException;
	
	Optional<List<User>> getAllUsers();
	
	Optional<User> getUser(long userid);
	
	Optional<User> register(String username, String password) throws UsernameTakenException;
	
	long deleteUser(String userName);

	void updateUserPassword(String username, String newPassword);

	void updateUserName(String oldUserName, String newUserName);
	
}
