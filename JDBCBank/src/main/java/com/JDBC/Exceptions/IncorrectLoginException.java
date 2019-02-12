package com.JDBC.Exceptions;

public class IncorrectLoginException extends Exception {
	public IncorrectLoginException(String errorMsg) 
	{
		super(errorMsg);
	}
}
