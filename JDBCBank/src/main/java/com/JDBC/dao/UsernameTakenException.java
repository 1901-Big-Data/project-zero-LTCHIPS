package com.JDBC.dao;

public class UsernameTakenException extends Throwable {
	public UsernameTakenException(String errormsg) 
	{
		super(errormsg);
		
	}
}
