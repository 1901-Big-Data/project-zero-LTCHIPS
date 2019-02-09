package com.JDBC.dao;

import java.util.List;
import java.util.Optional;

import com.JDBC.Exceptions.InsufficientFundsException;
import com.JDBC.model.BankAccount;

public interface IBankAccountDAO 
{	
	
	Optional<List<BankAccount>> getAllBankAccounts();
	
	Optional<List<BankAccount>> getAllUsersBankAccount(long userId);
	
	void depositIntoBankAccount(double amount, long accountid);
	
	void withdrawlFunds(double amountToWithdrawl, long accountid) throws InsufficientFundsException;
	
	Optional<BankAccount> addBankAccount(String bankAccountName, long userid);
	
	void updateBankAccount(BankAccount accnt, String [] params);
	
	void deleteBankAccount(long accountId);
}
