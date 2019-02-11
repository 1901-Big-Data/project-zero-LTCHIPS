package com.JDBC.Exceptions;

public class NoUserAccountsToDeleteException extends Throwable {
	public NoUserAccountsToDeleteException(String errorMsg) 
	{
		super(errorMsg);
		
	}
}
