package com.JDBC.Exceptions;

public class NegativeDepositException extends RuntimeException {
	public NegativeDepositException(String errorMessage) 
	{
		super(errorMessage);
	}
	
}
