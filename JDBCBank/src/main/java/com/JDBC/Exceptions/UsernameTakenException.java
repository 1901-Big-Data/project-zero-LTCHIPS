package com.JDBC.Exceptions;

public class UsernameTakenException extends RuntimeException {
	public UsernameTakenException(String errormsg) 
	{
		super(errormsg);
		
	}
}
