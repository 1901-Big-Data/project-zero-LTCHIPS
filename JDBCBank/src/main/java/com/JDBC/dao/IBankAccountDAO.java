package com.JDBC.dao;

import java.util.List;
import java.util.Optional;

import com.JDBC.Exceptions.InsufficientFundsException;
import com.JDBC.model.BankAccount;

public interface IBankAccountDAO 
{	
	
	Optional<List<BankAccount>> getAllBankAccounts();
	
	Optional<List<BankAccount>> getAllUsersBankAccount(long userId) throws Exception;
	
	public void depositFunds(long fundsArg);
	
	public void withdrawlFunds(long amountToWithdrawl) throws InsufficientFundsException;
	
	void saveBankAccount(BankAccount accnt);
	
	void updateBankAccount(BankAccount accnt, String [] params);
	
	void deleteBankAccount(BankAccount bnk);
}
