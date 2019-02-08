package com.JDBC.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.SQLException;
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
			String sql = "select * from users;";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			List<User> listOfUsers = new ArrayList<User>();

			while (rs.next()) {
				listOfUsers.add(new User( rs.getString("username"), rs.getLong(0)));
			}
			
			return Optional.of(listOfUsers);
			//return log.traceExit(Optional.of(listOfChampions));
		} catch (SQLException e) {
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
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(User User, String[] params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(User User) {
		// TODO Auto-generated method stub
		
	}

}
