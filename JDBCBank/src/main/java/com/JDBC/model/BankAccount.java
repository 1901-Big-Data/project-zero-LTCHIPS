package com.JDBC.model;

import com.JDBC.Exceptions.InsufficientFundsException;

public class BankAccount
{
	private long accountID;
	private long funds;
	
	
	public BankAccount(long accountIDArg, long fundsArg)
	{
		accountID = accountIDArg;
		funds = fundsArg;
		
	}
	
	public long getBalance() 
	{
		return funds;
	}
	
	public long getAccountID() 
	{
		return accountID;
	}
	
	public boolean isBalanceEmpty() 
	{
		return (funds == 0) ? true : false;
	}
	
	public void depositFunds(long fundsArg) 
	{
		if (fundsArg < 0) 
		{
			throw new IllegalArgumentException("Cannot deposit a negative number!");
			
		} 
		else 
		{
			funds+=fundsArg;
		}
		
	}
	
	public void withdrawlFunds(long amountToWithdrawl) throws InsufficientFundsException 
	{
		
		
		if((funds-amountToWithdrawl) < 0) 
		{
			throw new InsufficientFundsException("Insufficient funds.");
		}
		else 
		{
			funds-=amountToWithdrawl;
		}
	}
	
}

