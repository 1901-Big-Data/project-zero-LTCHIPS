package com.JDBC.dao;

import java.util.List;
import java.util.Optional;

import com.JDBC.Exceptions.InsufficientFundsException;
import com.JDBC.Exceptions.NegativeDepositException;
import com.JDBC.model.BankAccount;

public interface IBankAccountDAO 
{	
	
	Optional<List<BankAccount>> getAllBankAccounts();
	
	Optional<List<BankAccount>> getAllUsersBankAccount(long userId);
	
	void depositIntoBankAccount(double amount, long accountid) throws NegativeDepositException;
	
	void withdrawlFunds(double amountToWithdrawl, long accountid) throws InsufficientFundsException, NegativeWithdrawlException;
	
	Optional<BankAccount> addBankAccount(String bankAccountName, long userid);
	
	void deleteBankAccount(long accountId);
}
