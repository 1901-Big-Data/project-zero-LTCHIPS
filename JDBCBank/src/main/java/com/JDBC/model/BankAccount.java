package com.JDBC.model;

public class BankAccount
{
	private long accountID;
	private double funds;
	private String name;
	
	
	public BankAccount(long accountIDArg, double fundsArg, String nameArg)
	{
		accountID = accountIDArg;
		funds = fundsArg;
		name = nameArg;
		
	}
	
	public double getBalance() 
	{
		return funds;
	}
	
	public long getAccountID() 
	{
		return accountID;
	}
	
	public String getName() 
	{
		return name;
		
	}
	
	public boolean isBalanceEmpty() 
	{
		return (funds == 0) ? true : false;
	}
	
}

