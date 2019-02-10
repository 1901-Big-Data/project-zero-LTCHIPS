package com.JDBC.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;

import com.JDBC.Exceptions.InsufficientFundsException;
import com.JDBC.model.BankAccount;
import com.JDBC.model.SuperUser;
import com.JDBC.model.User;
import com.JDBC.service.BankAccountService;
import com.JDBC.service.UserService;
import com.JDBC.util.ScannerSingleton;


public class App 
{
	
	public static void printGreeting() 
	{
		
		LocalDateTime now = LocalDateTime.now();
		
		System.out.println("Welcome to JDBC Banking!");
		System.out.print("The date is ");
		System.out.print(now.getMonth().toString() + " ");
		System.out.print(now.getDayOfMonth() + " ");
		System.out.print(now.getYear());
		
		System.out.println();
		
	}
	
	
	public static SuperUser SuperLogin(String usernameArg, String passwordArg) 
	{
		
		InputStream in;
		
		try {
			// load information from properties file
			Properties props = new Properties();
			in = new FileInputStream(
					"C:\\Users\\LTCHIPS\\Documents\\Revature\\Project0\\project-zero-LTCHIPS\\JDBCBank\\src\\main\\java\\com\\JDBCBank\\Resource\\admin.properties");
			props.load(in);
			
			String username = props.getProperty("jdbc.admin.username");
			String password = props.getProperty("jdbc.admin.password");
			
			if (usernameArg.equals(username)) 
			{
				if(passwordArg.equals(password)) 
				{
					return new SuperUser(username, (long)1234);
				}
			}
			else 
			{
				return null;
			}

		}  catch (IOException ioe) 
		{
			//System.out.println("There was a problem reading the config file for database connection.");
			return null;
		}
		return null;
		
		
	}
	public static User LoopLogin()
	{		
		Scanner inputScan = ScannerSingleton.getScanner();
		
		UserService userService = UserService.getService();
		
		User returnUser;
		
		System.out.print("Username: ");
			
		String userNameIn = inputScan.next();
			
		System.out.print("Password: ");
			
		String passwordIn = inputScan.next();
			
		try
		{
			returnUser = SuperLogin(userNameIn, passwordIn);
			
			if (returnUser == null)
				returnUser = userService.login(userNameIn, passwordIn).get();
		}
		catch(NoSuchElementException nsee) 
		{
			System.out.println("Username or password incorrect.");
			return null;
		}
		
		return returnUser;
		
	}
	
	public static void PrintBankAccounts(User curUser) 
	{
		
		BankAccountService bankService = BankAccountService.getService();
		List<BankAccount> userBankAccounts = bankService .getAllUsersBankAccounts(curUser.getUserID()).get();
		
		if (userBankAccounts.isEmpty()) 
		{
			System.out.println("User currently has no bank accounts");
			
		}
		
		
		System.out.println(curUser.getUserName() + "'s Current Bank Accounts: \n");
		for (int x = 0; x < userBankAccounts.size(); x++) 
		{
			System.out.print((x + 1) + ":");
			
			System.out.print("Name: ");
			System.out.print(userBankAccounts.get(x).getName());
			System.out.println("\t \t");
			
			System.out.print("Funds: $");
			System.out.print(userBankAccounts.get(x).getBalance());
			System.out.println();
			
		}
		
	}
	
	public static void dashboard(User curUser) 
	{
		Scanner inputScan = ScannerSingleton.getScanner();
		
		while(true) 
		{
			
			System.out.println("=====================MAIN MENU=======================");
			System.out.println("1. Create an account");
			System.out.println("2. Views your current account balances");
			System.out.println("3. Deletes current account");
			System.out.println("4. Make a deposit to an existing account");
			System.out.println("5. Make a withdrawl from current account");
			System.out.println("6. Logout");
			
			System.out.print(">");
			
			if (inputScan.nextInt() == 1) 
			{
				CreateAccount(curUser);	
			}
			else if (inputScan.nextInt() == 2) 
			{
				PrintBankAccounts(curUser);
			}
			else if (inputScan.nextInt() == 3) 
			{
				DeleteAccount(curUser);	
			}
			else if (inputScan.nextInt() == 4) 
			{
				DepositIntoAccount(curUser);
			}
			else if (inputScan.nextInt() == 5) 
			{
				WithdrawFromAccount(curUser);
			}
			else if (inputScan.nextInt() == 6) 
			{
				System.out.println("Logging off, have a nice day!");
				break;
			}
			
		}
		
	}
	
	private static void WithdrawFromAccount(User curUser) {
		BankAccountService bankService = BankAccountService.getService();
		
		Scanner inputScan = ScannerSingleton.getScanner();
		
		
		List<BankAccount> userBankAccounts = bankService.getAllUsersBankAccounts(curUser.getUserID()).get();
		
		BankAccount bankAccount;
		
		if (userBankAccounts.size() > 1) 
		{
			PrintBankAccounts(curUser, userBankAccounts);
			System.out.println();
			
			
			int chosenAccount = 0;
			
			while(true) 
			{
				System.out.print("Choose Account to withdraw from: ");
				chosenAccount = inputScan.nextInt();
				if (chosenAccount - 1 >= userBankAccounts.size() || chosenAccount - 1 < 0)
				{
					System.out.println("Invalid choice");
					
				}
				else 
				{
					break;
				}
			}	
			
			bankAccount = userBankAccounts.get(chosenAccount - 1);
			
		}
		else 
		{
			bankAccount = userBankAccounts.get(0);
		}
		
		System.out.print("Enter amount to Withdraw: ");
		
		double amountToWithdraw = inputScan.nextDouble();
		
		try {
			bankService.withdrawFromBankAccount(amountToWithdraw, bankAccount.getAccountID());
		} catch (InsufficientFundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void PrintBankAccounts(User curUser, List<BankAccount> userBankAccounts) 
	{
		if (userBankAccounts.isEmpty()) 
		{
			System.out.println("User currently has no bank accounts");
			
		}
		
		
		System.out.println(curUser.getUserName() + "'s Current Bank Accounts: \n");
		for (int x = 0; x < userBankAccounts.size(); x++) 
		{
			System.out.print((x + 1) + ":");
			
			System.out.print("Name: ");
			System.out.print(userBankAccounts.get(x).getName());
			System.out.println("\t \t");
			
			System.out.print("Funds: $");
			System.out.print(userBankAccounts.get(x).getBalance());
			System.out.println();
			
		}
		
	}

	public static void DepositIntoAccount(User curUser) {
		BankAccountService bankService = BankAccountService.getService();
		
		Scanner inputScan = ScannerSingleton.getScanner();
		
		
		List<BankAccount> userBankAccounts = bankService .getAllUsersBankAccounts(curUser.getUserID()).get();
		
		long bankid;
		
		if (userBankAccounts.size() > 1) 
		{
			PrintBankAccounts(curUser, userBankAccounts);
			System.out.println();
			
			
			int chosenAccount = 0;
			
			while(true) 
			{
				System.out.print("Choose Account to deposit into: ");
				chosenAccount = inputScan.nextInt();
				if (chosenAccount - 1 >= userBankAccounts.size() || chosenAccount - 1 < 0)
				{
					System.out.println("Invalid choice");
					
				}
				else 
				{
					break;
				}
			}
			
			bankid = userBankAccounts.get(chosenAccount - 1).getAccountID();
			
		}
		else 
		{
			bankid = userBankAccounts.get(0).getAccountID();
		}
		
		System.out.print("Specify how much you wish to deposit: ");
		Double amountToDeposit = inputScan.nextDouble();
		
		bankService.depositIntoBankAccount(amountToDeposit, bankid);
		
		
	}

	public static void DeleteAccount(User curUser) {
		
		BankAccountService bankService = BankAccountService.getService();
		List<BankAccount> userBankAccounts = bankService.getAllUsersBankAccounts(curUser.getUserID()).get();
		
		BankAccount bankAccount;
		
		Scanner inputScan = ScannerSingleton.getScanner();
		
		if (userBankAccounts.size() > 1) 
		{
			PrintBankAccounts(curUser, userBankAccounts);
			System.out.println();
			
			
			int chosenAccount = 0;
			
			while(true) 
			{
				System.out.print("Choose Account to delete: ");
				chosenAccount = inputScan.nextInt();
				if (chosenAccount - 1 >= userBankAccounts.size() || chosenAccount - 1 < 0)
				{
					System.out.println("Invalid choice");
					
				}
				else 
				{
					break;
				}
			}	
			
			bankAccount = userBankAccounts.get(chosenAccount - 1);
			
		}
		else 
		{
			bankAccount = userBankAccounts.get(0);
		}
		
		if (bankAccount.getBalance() > 0.0) 
		{
			throw new RuntimeException("Cannot delete account that still has funds inside!");
			
		}
		
		bankService.deleteBankAccount(bankAccount.getAccountID());
		
	}

	public static void CreateAccount(User curUser) 
	{
		Scanner inputScan = ScannerSingleton.getScanner();
		
		BankAccountService bankService = BankAccountService.getService();
		
		
		System.out.println();
		System.out.print("Enter a name for the account: ");
		String newBankAccountName = inputScan.next();
		
		bankService.addBankAccount(newBankAccountName, curUser.getUserID());
	}

	public static User register() 
	{
		Scanner inputScan = ScannerSingleton.getScanner();
		
		UserService userService = UserService.getService();
		
		User newUser;
		
		while(true)
		{
			System.out.println("Enter a Username: ");
			
			String userNameIn = inputScan.next();
			
			String passwordIn = "";
			
			String passwordCheck = " ";
			
			while(!passwordIn.equals(passwordCheck)) 
			{
				System.out.print("Enter a Password: ");
				
				passwordIn = inputScan.next();
				
				System.out.print("Confirm Password: ");
				
				passwordCheck = inputScan.next();
				
			}			
			
			//TODO: COME UP WITH AN EXCEPTION TO THROW IF USERNAME ALREADY EXISTS

			//try 
			//{
				newUser = userService.register(userNameIn, passwordIn).get();
				
			//}
			//finally
			//{
				//inputScan.close();
				
			//}
			
			if (newUser != null) 
			{
				break;
				
			}
			
			
		}
		
		return newUser;
		
	}
	
    public static void main( String[] args )
    {
    	Scanner scanner = ScannerSingleton.getScanner();
    	
    	printGreeting();
    	
    	while(true)
    	{
    		System.out.println("Options: ");
    		System.out.println("1. Login");
    		System.out.println("2. Register");
    		System.out.println("3. Exit");
    		System.out.println();
    		
    		System.out.print(">");
    		
    		if (scanner.nextInt() == 1) 
    		{
    			User curUser = null;
    			
    			curUser = LoopLogin();
    			
    			if (curUser instanceof SuperUser) 
    			{
    				adminDashboard();
    			}
    			
    			else if (curUser instanceof User && curUser != null) 
    			{
    				 dashboard(curUser);
    				 
    			}
    		}
    		else if (scanner.nextInt() == 2) 
    		{
    			register();
    			
    		}
    		else if (scanner.nextInt() == 3) 
    		{
    			System.out.println("Exiting Application...");
    			
    			break;
    			
    		}
    		else
    		{
    			System.out.println("Invalid Command");
    		}
    		
    		
    	}
    	
    	try 
    	{
    		ScannerSingleton.killScanner();
    		
    	}
    	catch(Exception e) 
    	{
    		
    		
    	}
    	    	
    }


	private static void adminDashboard() {
		
		Scanner inputScan = ScannerSingleton.getScanner();
		//System.out.println("Admin login detected.");
		
		
		while(true) 
		{
			System.out.println("=====================ADMIN MENU=======================");
			System.out.println("1. View users");
			System.out.println("2. Create user");
			System.out.println("3. Update user");
			System.out.println("4. Delete user");
			System.out.println("5. Logout");
			
			if (inputScan.nextInt() == 1) 
			{
				ViewUsers();
				
			}
			
		}
		
		
	}


	private static void ViewUsers() {
		UserService serv = UserService.getService();
		List<User> users;
		
		try {
			users = serv.getUsers().get();
		}
		catch(NoSuchElementException nsee) 
		{
			System.out.println("No users currently are avaliable.");
			return;
		}
		
		System.out.println("=========Current Users========");
		for(int x = 0; x < users.size(); x++) 
		{
			System.out.print("User: ");
			System.out.print(users.get(x).getUserName());
			System.out.print("\t \t " + "ID: ");
			System.out.println(users.get(x).getUserID());
			
		}
		System.out.println();
		
		
		
	}

	
}

