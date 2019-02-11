package com.JDBC.Exceptions;

public class NegativeDepositException extends Throwable {
	public NegativeDepositException(String errorMessage) 
	{
		super(errorMessage);
	}
	
}
