package com.JDBC.util;

import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionUtility 
{
	private static Connection connectionInstance = null;
	
	private ConnectionUtility() {}
	
	public static Connection getConnection() 	{
		if (ConnectionUtility.connectionInstance != null) 
		{
			return ConnectionUtility.connectionInstance;
		}
		
		InputStream in = null;

		try {
			// load information from properties file
			Properties props = new Properties();
			in = new FileInputStream(
					"C:\\Users\\LTCHIPS\\Documents\\Revature\\Project0\\project-zero-LTCHIPS\\JDBCBank\\src\\main\\java\\com\\JDBCBank\\Resource\\connection.properties");
			props.load(in);

			// get the connection object
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = null;

			String endpoint = props.getProperty("jdbc.url");
			String username = props.getProperty("jdbc.username");
			String password = props.getProperty("jdbc.password");

			con = DriverManager.getConnection(endpoint, username, password);
			ConnectionUtility.connectionInstance = con;
			return connectionInstance;
		}  catch (IOException ioe) 
		{
			System.out.println("There was a problem connecting to the database.");
			
		} catch(SQLException sqle) 
		{
			System.out.println("an sql exception has occured.");
			
		} catch(ClassNotFoundException cnfe) 
		{
			System.out.println("A class was not found (likely something wrong with JDBC driver)");
		}
		//finally {
			//try {
				//in.close();
			//} catch (IOException e) {

			//}
		//}

		return null;
	}
	
}