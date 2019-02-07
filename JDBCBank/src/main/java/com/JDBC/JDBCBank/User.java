package com.JDBC.JDBCBank;

import java.util.Comparator;
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
	
	//TODO: add throws statement to method in case accountID already exists
	public void addAccountID(long accountID) 
	{
		accountIDs.add(accountID);
		
	}
	
}

class UserCompare implements Comparator<User>
{
	public int compare(User u1, User u2)
	{
		return u1.getUserID().compareTo(u2.getUserID());
		
	}
}