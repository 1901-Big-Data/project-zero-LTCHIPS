package com.JDBC.Exceptions;

public class NegativeWithdrawlException extends RuntimeException {

	public NegativeWithdrawlException(String errorMsg) 
	{
		super(errorMsg);
		
	}
}
