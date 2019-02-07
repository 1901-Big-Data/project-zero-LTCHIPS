package com.JDBC.JDBCBank;

import java.util.Comparator;

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
		//TODO: create an exception 
		funds+=fundsArg;
	}
	
	public void withdrawlFunds(long amountToWithdrawl) throws InsufficientFundsException 
	{
		if((funds-=amountToWithdrawl) < 0) 
		{
			throw new InsufficientFundsException("Insufficient funds.");
		}
	}
	
}

class BankAccountComparator implements Comparator<BankAccount>
{
	@Override
	public int compare(BankAccount o1, BankAccount o2) 
	{
		return (o1.getAccountID() == o2.getAccountID()) ? 0 : -1;
	}
}
