package com.JDBC.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.JDBC.Exceptions.InsufficientFundsException;
import com.JDBC.model.BankAccount;
import com.JDBC.model.User;
import com.JDBC.util.ConnectionUtility;

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
		
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) {
			return Optional.empty();
		}

		try {
			String sql = "select * from bankaccount;";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			List<BankAccount> listOfBankAccounts = new ArrayList<BankAccount>();

			while (rs.next()) 
			{
				listOfBankAccounts.add(new BankAccount( rs.getLong(1), rs.getLong(4), rs.getString(2)));
			}
			
			return Optional.of(listOfBankAccounts);
			//return log.traceExit(Optional.of(listOfChampions));
		} catch (SQLException e) 
		{
			//log.catching(e);
			//log.error("Sql Exception ovcured", e);
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public Optional<List<BankAccount>> getAllUsersBankAccount(long userId) {
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) {
			return Optional.empty();
		}

		try {
			String sql = "select * from bankaccount where userid = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setLong(1, userId);
			
			ResultSet rs = ps.executeQuery();

			List<BankAccount> listOfBankAccounts = new ArrayList<BankAccount>();

			while (rs.next()) 
			{
				listOfBankAccounts.add(new BankAccount( rs.getLong(1), rs.getLong(4), rs.getString(2)));
			}
			
			return Optional.of(listOfBankAccounts);
			//return log.traceExit(Optional.of(listOfChampions));
		} catch (SQLException e) 
		{
			//log.catching(e);
			//log.error("Sql Exception ovcured", e);
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
		
	}

	@Override
	public void depositIntoBankAccount(double amount, long accountid) 
	{
		if (amount < 0.0) 
		{
			throw new IllegalArgumentException("Cannot deposit negative amounts into account!");
		}
		
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null)
			return;
		//{
			//return Optional.empty();
			
		//}
		try 
		{
			
			String query = "CALL deposit(?, ?)"; //Should return the generated bankaccount id...
			
			CallableStatement queryStmt = con.prepareCall(query);
			
			queryStmt.setDouble(2, amount);
			
			queryStmt.setLong(1,accountid);
			
			//queryStmt.registerOutParameter(3, Types.NUMERIC);
			
			queryStmt.execute();
			
			//long bankAccountId = queryStmt.getLong(3);
			
			//return Optional.of(new BankAccount(bankAccountId, 0, bankAccountName));
			
		} catch(SQLException e) 
		{
			System.out.println("depositIntoBankAccount: SQL exception");
			
		}
		//return Optional.empty();
		
		
		
	}

	@Override
	public void withdrawlFunds(double amountToWithdrawl, long accountid) {
		
		if (amountToWithdrawl < 0.0) 
		{
			throw new IllegalArgumentException("Cannot withdraw negative amounts from account!");
		}
		
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null)
			return;
		//{
			//return Optional.empty();
			
		//}
		try
		{
			
			String query = "CALL withdraw(?, ?)"; //Should return the generated bankaccount id...
			
			CallableStatement queryStmt = con.prepareCall(query);
			
			queryStmt.setDouble(2, amountToWithdrawl);
			
			queryStmt.setLong(1,accountid);
			
			//queryStmt.registerOutParameter(3, Types.NUMERIC);
			
			queryStmt.execute();
			
			//long bankAccountId = queryStmt.getLong(3);
			
			//return Optional.of(new BankAccount(bankAccountId, 0, bankAccountName));
			
		} catch(SQLException e) 
		{
			System.out.println("withdrawlFunds: SQL exception");
			
		}
		//return Optional.empty();
		
		
		
	}

	@Override
	public Optional<BankAccount> addBankAccount(String bankAccountName, long userid) {
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) 
		{
			return Optional.empty();
			
		}
		try 
		{
			
			String query = "CALL bankinsert(?, ?, ?)"; //Should return the generated bankaccount id...
			
			CallableStatement queryStmt = con.prepareCall(query);
			
			queryStmt.setString(1, bankAccountName);
			
			queryStmt.setLong(2,  userid);
			
			queryStmt.registerOutParameter(3, Types.NUMERIC);
			
			queryStmt.execute();
			
			long bankAccountId = queryStmt.getLong(3);
			
			return Optional.of(new BankAccount(bankAccountId, 0, bankAccountName));
			
		} catch(SQLException e) 
		{
			System.out.println("addBankAccount: SQL exception");
			
		}
		return Optional.empty();
		
	}

	@Override
	public void updateBankAccount(BankAccount accnt, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteBankAccount(long accountId) 
	{
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null)
			return;
		try
		{
			
			String query = "CALL removeAccount(?)"; //Should return the generated bankaccount id...
			
			CallableStatement queryStmt = con.prepareCall(query);
			
			queryStmt.setLong(1, accountId);
			
			//queryStmt.registerOutParameter(3, Types.NUMERIC);
			
			queryStmt.execute();
			
			//long bankAccountId = queryStmt.getLong(3);
			
			//return Optional.of(new BankAccount(bankAccountId, 0, bankAccountName));
			
		} catch(SQLException e) 
		{
			System.out.println("deleteBankAccount: SQL exception");
			
		}
		//return Optional.empty();
		
		
		
	}
	
	
}
