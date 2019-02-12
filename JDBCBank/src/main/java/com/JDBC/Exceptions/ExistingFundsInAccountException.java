package com.JDBC.Exceptions;

public class ExistingFundsInAccountException extends RuntimeException {
	public ExistingFundsInAccountException(String errorMsg) 
	{
		super(errorMsg);
		
	}
}
