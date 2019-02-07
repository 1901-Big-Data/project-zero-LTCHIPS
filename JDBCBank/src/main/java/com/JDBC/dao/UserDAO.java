package com.JDBC.dao;

import java.util.List;
import java.util.Optional;
import java.sql.SQLException;
import java.sql.Connection;


import com.JDBC.model.User;


public class UserDAO implements IUserDAO
{
	
	private static UserDAO userDAO;
	//todo: add logger (from log4j2)
	
	private UserDAO () 
	{
		
		
	}
	
	public static UserDAO getDao() 
	{
		if(userDAO == null) 
		{
			userDAO = new UserDAO();
		}
		
		return userDAO;
		
	}
	
	@Override
	public Optional<User> getUserByName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<List<User>> getAllUsers() {
		
		return null;
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(User User, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(User User) {
		// TODO Auto-generated method stub
		
	}

}
