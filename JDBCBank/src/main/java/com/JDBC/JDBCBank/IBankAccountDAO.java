package com.JDBC.JDBCBank;

import java.util.List;
import java.util.Optional;

public interface IBankAccountDAO 
{	
	Optional<BankAccount> getBankAccountById(long bankAccountId);
	
	List<BankAccount> getAllBankAccounts();
	
	List<BankAccount> getAllUsersBankAccount(long userId) throws Exception;
	
	public void depositFunds(long fundsArg);
	
	public void withdrawlFunds(long amountToWithdrawl) throws InsufficientFundsException;
	
	void saveBankAccount(BankAccount accnt);
	
	void updateBankAccount(BankAccount accnt, String [] params);
	
	void deleteBankAccount(BankAccount bnk);
}
