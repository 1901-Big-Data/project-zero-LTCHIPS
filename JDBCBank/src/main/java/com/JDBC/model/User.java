package com.JDBC.model;

public class User 
{
	protected String userName;

	protected Long userID;
	
	public User(String userNameArg, long userIDArg) 
	{
		
		userID = userIDArg;
		
		userName = userNameArg;
		
	}
	
	public Long getUserID() 
	{
		return userID;
		
	}

	public String getUserName() 
	{
		return userName;
	}
	
}
