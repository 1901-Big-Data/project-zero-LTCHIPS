package com.JDBC.Exceptions;

public class InsufficientFundsException extends Throwable {

	public InsufficientFundsException(String errorMessage) 
	{
		super(errorMessage);
	}
}
