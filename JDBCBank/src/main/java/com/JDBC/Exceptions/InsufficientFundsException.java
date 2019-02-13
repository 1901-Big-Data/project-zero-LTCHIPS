package com.JDBC.Exceptions;

public class InsufficientFundsException extends RuntimeException{

	public InsufficientFundsException(String errorMessage) 
	{
		super(errorMessage);
	}
}
