package com.JDBC.dao;

import java.util.List;
import java.util.Optional;

import com.JDBC.Exceptions.InsufficientFundsException;
import com.JDBC.model.BankAccount;

public class BankAccountDAO implements IBankAccountDAO {

	private static BankAccountDAO dao;
	
	private BankAccountDAO() 
	{
		
		
		
	}
	
	public static BankAccountDAO getDao() 
	{
		if (dao == null) 
		{
			dao = new BankAccountDAO();
			
		}
		return dao;
		
	}

	@Override
	public Optional<List<BankAccount>> getAllBankAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<List<BankAccount>> getAllUsersBankAccount(long userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void depositFunds(long fundsArg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void withdrawlFunds(long amountToWithdrawl) throws InsufficientFundsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveBankAccount(BankAccount accnt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBankAccount(BankAccount accnt, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBankAccount(BankAccount bnk) {
		// TODO Auto-generated method stub
		
	}
	
	
}
