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

import com.JDBC.Exceptions.NegativeDepositException;
import com.JDBC.Exceptions.NegativeWithdrawlException;
import com.JDBC.model.BankAccount;
import com.JDBC.util.ConnectionUtility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class BankAccountDAO implements IBankAccountDAO {

	private static BankAccountDAO dao;
	
	private static final Logger log = LogManager.getLogger(BankAccountDAO.class);
	
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
		log.traceEntry();
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) {
			log.traceExit(Optional.empty());
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
		} catch (SQLException e) 
		{
			log.catching(e);
			log.error("An SQLException occured.", e);
		}
		log.traceExit(Optional.empty());
		return Optional.empty();
	}

	@Override
	public Optional<List<BankAccount>> getAllUsersBankAccount(long userId) {
		log.traceEntry();
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) {
			log.traceExit(Optional.empty());
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
			
			return log.traceExit(Optional.of(listOfBankAccounts));
		} catch (SQLException e) 
		{
			log.catching(e);
			log.error("An SQLException has occured." , e);
		}
		log.traceExit(Optional.empty());
		return Optional.empty();
		
	}
	
	@Override
	public Optional<BankAccount> getBankAccount(long bankAccountId) {
		log.traceEntry();
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "select * from bankaccount where accountid = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setLong(1, bankAccountId);
			
			ps.executeQuery();
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			return log.traceExit(Optional.of(new BankAccount(rs.getLong(1), rs.getLong(4), rs.getString(2))));
		} catch (SQLException e) 
		{
			log.catching(e);
			log.error("An SQLException has occured." , e);
		}
		log.traceExit(Optional.empty());
		return Optional.empty();
		
	}
	
	@Override
	public Optional<BankAccount> getBankAccount(String bankAccountName) {
		log.traceEntry();
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) {
			log.traceExit(Optional.empty());
			return Optional.empty();
		}

		try {
			String sql = "select * from bankaccount where accountname = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			
			ps.setString(1, bankAccountName);
			
			ps.executeQuery();
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			
			return log.traceExit(Optional.of(new BankAccount(rs.getLong(1), rs.getLong(4), rs.getString(2))));
		} catch (SQLException e) 
		{
			log.catching(e);
			log.error("An SQLException has occured." , e);
		}
		log.traceExit(Optional.empty());
		return Optional.empty();
		
	}

	@Override
	public void depositIntoBankAccount(double amount, long accountid) throws NegativeDepositException 
	{
		log.traceEntry();
		if (amount < 0.0) 
		{
			NegativeDepositException nde = new NegativeDepositException("Cannot deposit negative amounts into account!");
			log.catching(nde);
			log.error(nde.getMessage(), nde);
			throw nde;
		}
		
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) {
			log.traceExit();
			return;
		}
		try 
		{
			
			String query = "CALL deposit(?, ?)";
			
			CallableStatement queryStmt = con.prepareCall(query);
			
			queryStmt.setDouble(2, amount);
			
			queryStmt.setLong(1,accountid);
			
			queryStmt.execute();
			
			log.traceExit();
			
		} catch(SQLException e)
		{
			log.catching(e);
			log.error("An SQLException has occured.", e);
			
		}
		log.traceExit();
	}

	@Override
	public void withdrawlFunds(double amountToWithdrawl, long accountid) throws NegativeWithdrawlException {
		log.traceEntry();
		if (amountToWithdrawl < 0.0) 
		{
			NegativeWithdrawlException nwe = new NegativeWithdrawlException("Cannot withdraw negative amounts from account!");
			log.catching(nwe);
			log.error(nwe.getMessage(), nwe);
			throw nwe;
		}
		
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) {
			log.traceExit();
			return;
		}
		try
		{
			
			String query = "CALL withdraw(?, ?)";
			
			CallableStatement queryStmt = con.prepareCall(query);
			
			queryStmt.setDouble(2, amountToWithdrawl);
			
			queryStmt.setLong(1,accountid);
			
			queryStmt.execute();
			
		} catch(SQLException e)
		{
			log.catching(e);
			log.error("An SQLException has occured.", e);
			
		}
		
		log.traceExit();
		
	}

	@Override
	public Optional<BankAccount> addBankAccount(String bankAccountName, long userid) {
		log.traceEntry();
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) {
			log.traceExit();
			return Optional.empty();
		}
		try 
		{
			
			String query = "CALL bankinsert(?, ?, ?)";
			
			CallableStatement queryStmt = con.prepareCall(query);
			
			queryStmt.setString(1, bankAccountName);
			
			queryStmt.setLong(2,  userid);
			
			queryStmt.registerOutParameter(3, Types.NUMERIC);
			
			queryStmt.execute();
			
			long bankAccountId = queryStmt.getLong(3);
			
			return log.traceExit(Optional.of(new BankAccount(bankAccountId, 0, bankAccountName)));
			
		} catch(SQLException e)
		{
			log.catching(e);
			log.error("An SQLException has occured.", e);
			
		}
		log.traceExit(Optional.empty());
		return Optional.empty();
		
	}

	@Override
	public void deleteBankAccount(long accountId) 
	{
		log.traceEntry();
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) {
			log.traceExit();
			return;
		}
		try
		{
			
			String query = "CALL removeAccount(?)";
			
			CallableStatement queryStmt = con.prepareCall(query);
			
			queryStmt.setLong(1, accountId);
			
			queryStmt.execute();
			
			log.traceExit();
						
		} 
		catch(SQLException e)
		{
			log.catching(e);
			log.error("An SQLException has occured.", e);
		}
		log.traceExit();
	}
}
