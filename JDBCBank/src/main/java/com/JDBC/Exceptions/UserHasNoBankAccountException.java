package com.JDBC.Exceptions;

public class UserHasNoBankAccountException extends Exception {
	public UserHasNoBankAccountException(String errorMsg) 
	{
		super(errorMsg);
		
	}
}
