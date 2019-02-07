package com.JDBC.model;

import java.util.HashSet;

public class User 
{
	protected String userName;

	protected Long userID;
	
	protected HashSet<Long> accountIDs;
	
	public User(String userNameArg, long userIDArg, HashSet<Long> accountIDsArg) 
	{
		
		userID = userIDArg;
		
		userName = userNameArg;
		
		accountIDs = accountIDsArg;
		
	}
	
	public Long getUserID() 
	{
		return userID;
		
	}
	
	public HashSet<Long> getAccountIDs() 
	{
		return accountIDs;
	}
	
	public String getUserName() 
	{
		return userName;
	}
	
}
