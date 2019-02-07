package com.JDBC.service;

import java.util.List;
import java.util.Optional;

import com.JDBC.dao.UserDAO;
import com.JDBC.model.User;

public class UserService {

	private static UserService userServ;
	
	final static UserDAO userDAO = UserDAO.getDao();
	
	private UserService()
	{
		//constructor stuff
		
		
	}

	public static UserService getService() 
	{
		if (userServ == null) 
		{
			userServ = new UserService();
			
		}
		return userServ;
		
	}
	
	public Optional<List<User>> getUsers()
	{
		return userDAO.getAllUsers();
		
	}
	
	public Optional<User> getUserByName(String name)
	{
		return userDAO.getUserByName(name);
		
	}
	
	
}
