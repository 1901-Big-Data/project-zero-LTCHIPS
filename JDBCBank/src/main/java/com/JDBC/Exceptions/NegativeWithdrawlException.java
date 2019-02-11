package com.JDBC.Exceptions;

public class NegativeWithdrawlException extends Throwable {

	public NegativeWithdrawlException(String errorMsg) 
	{
		super(errorMsg);
		
	}
}
