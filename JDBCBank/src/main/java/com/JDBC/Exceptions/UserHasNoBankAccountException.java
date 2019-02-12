package com.JDBC.Exceptions;

public class UserHasNoBankAccountException extends RuntimeException {
	public UserHasNoBankAccountException(String errorMsg) 
	{
		super(errorMsg);
		
	}
}
