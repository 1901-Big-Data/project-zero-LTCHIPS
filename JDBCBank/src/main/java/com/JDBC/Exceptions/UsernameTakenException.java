package com.JDBC.Exceptions;

public class UsernameTakenException extends Throwable {
	public UsernameTakenException(String errormsg) 
	{
		super(errormsg);
		
	}
}
