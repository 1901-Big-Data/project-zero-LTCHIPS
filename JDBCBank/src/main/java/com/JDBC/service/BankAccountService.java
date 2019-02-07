package com.JDBC.service;

import java.util.List;
import java.util.Optional;

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
		
		
		
		return null;
	}
	
	public Optional<BankAccount> getBankAccountByUserId(long id)
	{
		return null;
		
	}
	
	
}
