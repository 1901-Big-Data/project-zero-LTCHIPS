package com.JDBC.service;

import java.util.List;
import java.util.Optional;

import com.JDBC.Exceptions.IncorrectLoginException;
import com.JDBC.Exceptions.UsernameTakenException;
import com.JDBC.dao.UserDAO;
import com.JDBC.model.User;

public class UserService {

	private static UserService userServ;
	
	final static UserDAO userDAO = UserDAO.getDao();
	
	private UserService()
	{
		
		
	}

	public static UserService getService() 
	{
		if (userServ == null) 
		{
			userServ = new UserService();
			
		}
		return userServ;
		
	}
	
	public Optional<User> login(String username, String password)
	{
		return userDAO.login(username, password);
		
	}
	
	public Optional<User> register(String username, String password) throws UsernameTakenException
	{
		return userDAO.register(username, password);
		
	}
	
	public Optional<List<User>> getUsers()
	{
		return userDAO.getAllUsers();
		
	}
	
	public Optional<User> getUser(long userid)
	{
		return userDAO.getUser(userid);
	}
	
	public void updateUserPassword(String username, String newPassword) 
	{
		userDAO.updateUserPassword(username, newPassword);
		
	}
	
	public void updateUserName(String oldUserName, String newUserName) 
	{
		userDAO.updateUserName(oldUserName, newUserName);
		
	}
	
	public long deleteUser(String username) 
	{
		return userDAO.deleteUser(username);
	}
	
	
}
