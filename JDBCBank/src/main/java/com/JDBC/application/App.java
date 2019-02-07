package com.JDBC.application;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Scanner;

import com.JDBC.dao.BankAccountDAO;
import com.JDBC.dao.UserDAO;
import com.JDBC.model.User;


public class App 
{
	public static void printHelp() 
	{
		System.out.println("CREATE - Creates an account.");
		System.out.println("VIEW - Views your current account balances.");
		System.out.println("DELETE - Deletes current account.");
		System.out.println("DEPOSIT - Make a deposit to current account.");
		System.out.println("WITHDRAW - Make a withdrawl from current account.");
		System.out.println("HELP - Displays this hopefully helpful message.");
		
	}
	
	public static void printGreeting() 
	{
		
		LocalDateTime now = LocalDateTime.now();
		
		System.out.println("Welcome to JDBC Banking!");
		System.out.print("The date is ");
		System.out.print(now.getMonth().toString() + " ");
		System.out.print(now.getDayOfMonth() + " ");
		System.out.print(now.getYear());
		
		System.out.println();
		
		//System.out.print("The time is " );
		//System.out.print(now.getHour() % 12 + ":");
		//System.out.print(now.getMinute());
		
		//System.out.println();
		
	}
	
	public static void LoopLogin()
	{
		
		
	}
	
    public static void main( String[] args )
    {
    	Scanner inputScan = new Scanner(System.in);
    	
    	UserDAO userDAO = new UserDAO();
    	
    	BankAccountDAO bankAccountDAO = new BankAccountDAO();
    	
    	printGreeting();
    	
    	while(inputScan.next().toLowerCase().compareTo("exit") != 0) 
    	{
    		//System.out.print("Please enter a command: ");
    		
    		String inputStr = inputScan.next().toLowerCase();
    		
    		if (inputStr.equals("create")) 
    		{
    			System.out.println("*creates account*");
    			//do account creation stuff
    		}
    		else if(inputStr.equals("view")) 
    		{
    			System.out.println("*views account*");
    			//show accounts and balances
    		}
    		else if(inputStr.equals("delete"))
    		{
    			System.out.println("*deletes account*");
    			//delete account
    		}
    		else if(inputStr.equals("deposit"))
    		{
    			System.out.println("*deposits money*");
    			//deposit funds
    		}
    		else if(inputStr.equals("withdraw")) 
    		{
    			System.out.println("*withdraws money*");
    			//withdraw funds
    		}
    		else if(inputStr.equals("help")) 
    		{
    			printHelp();
    		}
    		else if (inputStr.equals("register")) 
    		{
    			System.out.print("Please give a User Name: ");
    			String username = inputScan.next();
    			
    			System.out.print("Please give a Password:");
    			
    			String password = inputScan.next();
    			
    			User newUser = new User(username, 123, new HashSet<Long>());
    			
    			userDAO.saveUser(newUser);
    			
    		}
    		else if (inputStr.equals("logout")) 
    		{
    			System.out.println("user has logged out");
    			//logout stuff
    		}
    		else
    		{
    			System.out.println("Invalid Command");
    			System.out.println("Type 'help' to see a list of valid commands.");
    		}
    		
    	}
    	
    	inputScan.close();
    	
    }
}
