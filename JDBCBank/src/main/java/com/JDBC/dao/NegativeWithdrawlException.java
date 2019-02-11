package com.JDBC.dao;

public class NegativeWithdrawlException extends Throwable {

	public NegativeWithdrawlException(String errorMsg) 
	{
		super(errorMsg);
		
	}
}
