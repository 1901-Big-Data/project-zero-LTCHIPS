package com.JDBC.service;

import java.util.List;
import java.util.Optional;

import com.JDBC.Exceptions.InsufficientFundsException;
import com.JDBC.dao.BankAccountDAO;

import com.JDBC.model.BankAccount;

public class BankAccountService {

	private static BankAccountService bankServ;
	
	final static BankAccountDAO bankDAO = BankAccountDAO.getDao();
	
	private BankAccountService() 
	{
		
		
	}
	
	public static BankAccountService getService() 
	{
		if (bankServ == null) 
		{
			bankServ = new BankAccountService();
			
		}
		return bankServ;
		
	}
	
	public Optional<List<BankAccount>> getAllBankAccounts()
	{	
		return bankDAO.getAllBankAccounts();
	}
	
	public Optional<List<BankAccount>> getAllUsersBankAccounts(long userid)
	{
		return bankDAO.getAllUsersBankAccount(userid);
	}
	
	public Optional<BankAccount> addBankAccount(String bankAccountName, long userid)
	{
		return bankDAO.addBankAccount(bankAccountName, userid);
	}
	
	public void depositIntoBankAccount(double amount, long accountid) 
	{
		bankDAO.depositIntoBankAccount(amount, accountid);
		
	}
	
	public void withdrawFromBankAccount(double amount, long accountid) throws InsufficientFundsException 
	{
		bankDAO.withdrawlFunds(amount, accountid);
		
	}
	
	public void deleteBankAccount(long accountId) 
	{
		bankDAO.deleteBankAccount(accountId);
		
	}
	
	
	
}
