package com.JDBC.Exceptions;

public class ExistingFundsInAccountException extends Throwable {
	public ExistingFundsInAccountException(String errorMsg) 
	{
		super(errorMsg);
		
	}
}
