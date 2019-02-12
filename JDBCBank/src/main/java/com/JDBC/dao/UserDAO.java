package com.JDBC.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.JDBC.Exceptions.UsernameTakenException;
import com.JDBC.model.User;
import com.JDBC.util.ConnectionUtility;


public class UserDAO implements IUserDAO
{
	private static UserDAO userDAO;
	
	private static final Logger log = LogManager.getLogger(UserDAO.class);
	
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
			return log.traceExit(Optional.empty());	
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
			
			return log.traceExit(Optional.of(loginUser));
			
		} catch(SQLException e) 
		{
			
			log.catching(e);
			log.error("A SQLException has occured.");
			
			/*
			 * try { con.close(); }catch(SQLException e1) { log.catching(e1); log.
			 * error("An exception occured while attemping to close the connection object."
			 * ); }
			 */
			
		}
		return log.traceExit(Optional.empty());
	}
	
	public Optional<User> getUser(long userid)
	{
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) 
		{
			return log.traceExit(Optional.empty());	
		}
		
		try 
		{
			String query = "SELECT * FROM users WHERE userid = ?";
			
			PreparedStatement queryStmt = con.prepareStatement(query);
			
			queryStmt.setLong(1, userid);
			
			ResultSet rs = queryStmt.executeQuery();
			
			rs.next();
			
			User user = new User(rs.getString("username"), rs.getLong("userid"));
			
			return log.traceExit(Optional.of(user));
			
		} catch(SQLException e) 
		{
			log.catching(e);
			log.error("A SQLException has occured.");
			
			/*
			 * try { con.close(); }catch(SQLException e1) { log.catching(e1); log.
			 * error("An exception occured while attemping to close the connection object."
			 * ); }
			 */
			
		}
		return log.traceExit(Optional.empty());
		
	}

	@Override
	public Optional<List<User>> getAllUsers() 
	{	
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) 
		{
			log.traceExit(Optional.empty());
			return log.traceExit(Optional.empty());
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
			
			return log.traceExit(Optional.of(listOfUsers));
		} catch (SQLException e) 
		{
			log.catching(e);
			log.error("An SQLException has occured.");
			/*
			 * try { con.close(); } catch (SQLException e1) { log.catching(e1); log.
			 * error("An exception occured while attempting to close the connection object"
			 * ); }
			 */
		}
		return log.traceExit(Optional.empty());
	}

	@Override
	public Optional<User> register(String username, String password) throws UsernameTakenException
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
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) 
			{
				UsernameTakenException ute = new UsernameTakenException("Username already taken!");
				log.catching(ute);
				throw ute;
				
			}
			else 
			{
				log.catching(e);
				log.error("An SQLException has occured.");
				/*
				 * try { con.close(); } catch (SQLException e1) { log.catching(e1); log.
				 * error("An exception occured while attempting to close the connection object"
				 * ); }
				 */
				log.traceExit(Optional.empty());
			}
		}
		return log.traceExit(Optional.empty());
		
	}

	@Override
	public void updateUserPassword(String username, String newPassword) {
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) 
		{
			log.traceExit();
			return;	
		}
		try 
		{
			String query = "CALL updateuserpassword(?, ?)"; //Should return the generated user id...
			
			CallableStatement queryStmt = con.prepareCall(query);
			
			queryStmt.setString(1,  username);
			
			queryStmt.setString(2,  newPassword);
			
			queryStmt.execute();
			
			log.traceExit();
			
		} catch(SQLException e) 
		{
			log.catching(e);
			log.error("An SQLException has occured");
			/*
			 * try { con.close(); } catch (SQLException e1) { log.catching(e1); log.
			 * error("An exception occured while attempting to close the connection object"
			 * ); }
			 */
		}
		log.traceExit();
	}
	
	@Override
	public void updateUserName(String oldUserName, String newUserName) 
	{
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) 
		{
			log.traceExit();
			return;	
		}
		try 
		{
			String query = "CALL updateusername(?, ?)";
			
			CallableStatement queryStmt = con.prepareCall(query);
			
			queryStmt.setString(1,  oldUserName);
			
			queryStmt.setString(2,  newUserName);
			
			queryStmt.execute();
			
			log.traceExit();
			
		} catch(SQLException e) 
		{
			if (e.getCause() instanceof SQLIntegrityConstraintViolationException) 
			{
				UsernameTakenException ute = new UsernameTakenException("Username already taken!");
				log.catching(ute);
				throw ute;
				
			}
			log.catching(e);
			log.error("An SQLException has occured");
			/*
			 * try { con.close(); } catch (SQLException e1) { log.catching(e1); log.
			 * error("An exception occured while attempting to close the connection object"
			 * ); }
			 */
		}
		log.traceExit();
		
	}

	@Override
	public long deleteUser(String userName) {
		Connection con = ConnectionUtility.getConnection();
		
		if (con == null) 
		{
			log.traceExit();
			return -1;	
		}
		try 
		{	
			String query = "CALL removeuser(?, ?)"; //Should return the generated user id...
			
			CallableStatement queryStmt = con.prepareCall(query);
			
			queryStmt.setString(1,  userName);
			
			long id = 0;
			
			queryStmt.registerOutParameter(2, Types.NUMERIC);
			
			queryStmt.execute();
			
			log.traceExit();
			
			return id;
			
			
		} catch(SQLException e)
		{
			log.catching(e);
			log.error("An SQLException has occured");
			/*
			 * try { con.close(); } catch (SQLException e1) { log.catching(e1); log.
			 * error("An exception occured while attempting to close the connection object"
			 * ); }
			 */
		}
		log.traceExit();
		return -1;
		
	}
}
