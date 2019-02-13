package com.JDBC.Exceptions;

public class NoUserAccountsToDeleteException extends RuntimeException {
	public NoUserAccountsToDeleteException(String errorMsg) 
	{
		super(errorMsg);
		
	}
}
