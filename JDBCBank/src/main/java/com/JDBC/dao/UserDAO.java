package com.JDBC.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.SQLException;
import java.sql.Types;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.JDBC.model.User;
import com.JDBC.util.ConnectionUtility;


public class UserDAO implements IUserDAO
{
	
	private static UserDAO userDAO;
	//todo: add logger (from log4j2)
	
	private UserDAO () 
	{
		
		
	}
	
	public static UserDAO getDao() 
	{
		if(userDAO == null) 
		{
			userDAO = new UserDAO();
		}
		
		return userDAO;
		
	}
	
	public Optional<User> login(String userName, String password)
	{
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) 
		{
			return Optional.empty();
			
		}
		
		try 
		{
			String query = "SELECT * FROM users WHERE username = ? and password = ?";
			
			PreparedStatement queryStmt = con.prepareStatement(query);
			
			queryStmt.setString(1,  userName);
			
			queryStmt.setString(2,  password);
			
			ResultSet rs = queryStmt.executeQuery();
			
			rs.next();
			
			User loginUser = new User(rs.getString("username"), rs.getLong("userid"));
			
			return Optional.of(loginUser);
			
		} catch(SQLException e) 
		{
			System.out.println("login: SQL exception");
			
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<User> getUserByName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<List<User>> getAllUsers() 
	{
		
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) {
			return Optional.empty();
		}

		try {
			String sql = "select * from users";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			List<User> listOfUsers = new ArrayList<User>();

			while (rs.next()) 
			{
				listOfUsers.add(new User( rs.getString("username"), rs.getLong(1)));
			}
			
			return Optional.of(listOfUsers);
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
	public Optional<User> register(String username, String password)
	{
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) 
		{
			return Optional.empty();
			
		}
		try 
		{
			
			String query = "CALL userinsert(?, ?, ?)"; //Should return the generated user id...
			
			CallableStatement queryStmt = con.prepareCall(query);
			
			queryStmt.setString(1,  username);
			
			queryStmt.setString(2,  password);
			
			queryStmt.registerOutParameter(3, Types.NUMERIC);
			
			queryStmt.execute();
			
			long userid = queryStmt.getLong(3);
			
			return Optional.of(new User(username, userid));
			
		} catch(SQLException e) 
		{
			System.out.println("login: SQL exception");
			
		}
		return Optional.empty();
		
	}

	@Override
	public void updateUser(User User, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String userName) {
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) 
		{
			//return Optional.empty();
			return;
			
		}
		try 
		{
			
			String query = "CALL removeuser(?)"; //Should return the generated user id...
			
			CallableStatement queryStmt = con.prepareCall(query);
			
			queryStmt.setString(1,  userName);
			
			//queryStmt.setString(2,  password);
			
			//queryStmt.registerOutParameter(3, Types.NUMERIC);
			
			queryStmt.execute();
			
			//long userid = queryStmt.getLong(3);
			
			//return Optional.of(new User(username, userid));
			
		} catch(SQLException e) 
		{
			System.out.println("deleteUser: SQL exception");
			
		}
		//return Optional.empty();
		
	}

}
