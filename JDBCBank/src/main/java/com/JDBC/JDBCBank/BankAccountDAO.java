package com.JDBC.JDBCBank;

import java.util.List;
import java.util.Optional;

public class BankAccountDAO implements IBankAccountDAO {

	List<BankAccount> bankAccounts;

	@Override
	public Optional<BankAccount> getBankAccountById(long bankAccountId) 
	{
		
		Optional<BankAccount> bankAccount = Optional.empty();
		
		for (BankAccount x : bankAccounts) 
		{
			if (x.getAccountID() == bankAccountId) 
			{
				bankAccount = Optional.of(x);
			}
		}
		
		
		return bankAccount;
	}

	@Override
	public List<BankAccount> getAllBankAccounts() 
	{
		return bankAccounts;
	}

	@Override
	public List<BankAccount> getAllUsersBankAccount(long userId) throws Exception 
	{
		throw new Exception("haven't implemented this yet");
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveBankAccount(BankAccount accnt) 
	{
		
		BankAccountComparator comp = new BankAccountComparator();
		
		for (int x = 0; x < bankAccounts.size(); x++) 
		{
			if (comp.compare(bankAccounts.get(x), accnt) == 0) 
			{
				bankAccounts.set(x, accnt);
				return;
			}
			
		}
		
		bankAccounts.add(accnt);
		
	}

	@Override
	public void updateBankAccount(BankAccount accnt, String[] params) 
	{
		
		BankAccountComparator comp = new BankAccountComparator();
		
		for(int x = 0; x < bankAccounts.size(); x++)
		{
			if (comp.compare(bankAccounts.get(x), accnt) == 0) 
			{
				bankAccounts.set(x,  accnt);
				return;
			}
		}
		
	}

	@Override
	public void deleteBankAccount(BankAccount bnk) 
	{
		BankAccountComparator comp = new BankAccountComparator();
		
		bankAccounts.removeIf(x -> comp.compare(bnk, x) == 0);
		
	}

	@Override
	public void depositFunds(long fundsArg) throws IllegalArgumentException
	{
		if (fundsArg < 0) 
		{
			throw new IllegalArgumentException("Cannot deposit a negative amount!");
			
		}
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void withdrawlFunds(long amountToWithdrawl)
	{
		// TODO Auto-generated method stub
		
	}
	 
	
}
