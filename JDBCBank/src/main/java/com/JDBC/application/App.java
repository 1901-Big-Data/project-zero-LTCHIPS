package com.JDBC.application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.JDBC.Exceptions.ExistingFundsInAccountException;
import com.JDBC.Exceptions.IncorrectLoginException;
import com.JDBC.Exceptions.InsufficientFundsException;
import com.JDBC.Exceptions.NegativeDepositException;
import com.JDBC.Exceptions.NegativeWithdrawlException;
import com.JDBC.Exceptions.NoUserAccountsToDeleteException;
import com.JDBC.Exceptions.UserHasNoBankAccountException;
import com.JDBC.Exceptions.UsernameTakenException;
import com.JDBC.dao.UserDAO;
import com.JDBC.model.BankAccount;
import com.JDBC.model.SuperUser;
import com.JDBC.model.User;
import com.JDBC.service.BankAccountService;
import com.JDBC.service.UserService;
import com.JDBC.util.ScannerSingleton;


public class App 
{
	
	private static final Logger log = LogManager.getLogger();
	
	private static void printGreeting() 
	{
		LocalDateTime now = LocalDateTime.now();
		
		System.out.println("Welcome to JDBC Banking!");
		System.out.print("The date is ");
		System.out.print(now.getMonth().toString() + " ");
		System.out.print(now.getDayOfMonth() + " ");
		System.out.print(now.getYear());
		
		System.out.println();
		
	}
	
	
	private static SuperUser SuperLogin(String usernameArg, String passwordArg) throws IOException, FileNotFoundException
	{
		InputStream in;
		
		// load information from properties file
		Properties props = new Properties();
		in = new FileInputStream(
				"C:\\Users\\LTCHIPS\\Documents\\Revature\\Project0\\project-zero-LTCHIPS\\JDBCBank\\src\\main\\java\\com\\JDBC\\Resources\\admin.properties");
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
		return null;
		
		
	}
	private static User LoopLogin()
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
		catch(IOException ioe) 
		{
			try {
				returnUser = userService.login(userNameIn, passwordIn).get();
			}
			catch(NoSuchElementException nsee)
			{
				System.out.println("Username or password incorrect.");
				return null;
			}
		}
		
		return returnUser;
		
	}
	
	private static void PrintBankAccounts(User curUser) 
	{
		
		BankAccountService bankService = BankAccountService.getService();
		List<BankAccount> userBankAccounts = bankService.getAllUsersBankAccounts(curUser.getUserID()).get();
		
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
	
	private static void dashboard(User curUser) 
	{
		Scanner inputScan = ScannerSingleton.getScanner();
		
		while(true) 
		{
			
			System.out.println("=====================MAIN MENU=======================");
			System.out.println("1. Create a bank account");
			System.out.println("2. Views your current account balances");
			System.out.println("3. Delete a bank account");
			System.out.println("4. Make a deposit to an existing account");
			System.out.println("5. Make a withdrawl from an existing account");
			System.out.println("6. Logout");
			
			System.out.print(">");
			try {
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
					try {
						DeleteAccount(curUser);
					}
					catch(NoUserAccountsToDeleteException nuatde) 
					{
						System.out.println(nuatde.getMessage());
					}
					catch(ExistingFundsInAccountException efinae) 
					{
						System.out.println(efinae.getMessage());
					}
				}
				else if (inputScan.nextInt() == 4) 
				{
					try {
						DepositIntoAccount(curUser);
					}
					catch(NegativeDepositException nde) 
					{
						System.out.println(nde.getMessage());
					}
					catch(UserHasNoBankAccountException uhnae) 
					{
						System.out.println(uhnae.getMessage());
					}
				
				}
				else if (inputScan.nextInt() == 5) 
				{
					try {
						WithdrawFromAccount(curUser);
					}
					catch(InsufficientFundsException ife) 
					{
						System.out.println(ife.getMessage());
					}
					catch(NegativeWithdrawlException nwe) 
					{
						System.out.println(nwe.getMessage());
					}
				}
				else if (inputScan.nextInt() == 6) 
				{
					System.out.println("Logging off, have a nice day!");
					break;
				}
			}
			catch(InputMismatchException ime) 
			{
				System.out.println("Invalid choice");
			}
		}
		
	}
	
	private static void WithdrawFromAccount(User curUser) throws InsufficientFundsException, NegativeWithdrawlException {
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
		
		if (bankAccount.getBalance() < amountToWithdraw) 
		{
			throw new InsufficientFundsException("Insufficient Funds");
		}
		
		bankService.withdrawFromBankAccount(amountToWithdraw, bankAccount.getAccountID()); 
		
	}

	private static void PrintBankAccounts(User curUser, List<BankAccount> userBankAccounts) 
	{
		if (userBankAccounts.isEmpty()) 
		{
			System.out.println("User currently has no bank accounts");
			
		}
		
		System.out.println(curUser.getUserName() + "'s Current Bank Accounts: \n");
		for (int x = 0; x < userBankAccounts.size(); x++) 
		{
			System.out.print((x + 1) + ": ");
			
			System.out.print("Name: ");
			System.out.print(userBankAccounts.get(x).getName());
			//System.out.print("\t");
			
			System.out.print("Funds: $");
			System.out.print(userBankAccounts.get(x).getBalance());
			System.out.println();
			
		}
		
	}

	private static void DepositIntoAccount(User curUser) throws NegativeDepositException, UserHasNoBankAccountException {
		BankAccountService bankService = BankAccountService.getService();
		
		Scanner inputScan = ScannerSingleton.getScanner();
		
		
		List<BankAccount> userBankAccounts = bankService.getAllUsersBankAccounts(curUser.getUserID()).get();
		
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
		else if (userBankAccounts.size() == 0) 
		{
			throw new UserHasNoBankAccountException("User does not have any bank accounts!");
			
		}
		
		else 
		{
			bankid = userBankAccounts.get(0).getAccountID();
		}
		
		
		
		System.out.print("Specify how much you wish to deposit: ");
		Double amountToDeposit = inputScan.nextDouble();
		
		bankService.depositIntoBankAccount(amountToDeposit, bankid);
		
	}

	private static void DeleteAccount(User curUser) throws NoUserAccountsToDeleteException, ExistingFundsInAccountException {
		
		BankAccountService bankService = BankAccountService.getService();
		List<BankAccount> userBankAccounts = bankService.getAllUsersBankAccounts(curUser.getUserID()).get();
		
		BankAccount bankAccount;
		
		Scanner inputScan = ScannerSingleton.getScanner();
		
		if (userBankAccounts.size() > 0) 
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
			throw new NoUserAccountsToDeleteException("There are no user accounts to delete.");
			
		}
		
		if (bankAccount.getBalance() > 0.0) 
		{
			throw new ExistingFundsInAccountException("Cannot delete account that still has funds inside!");
		}
		
		bankService.deleteBankAccount(bankAccount.getAccountID());
		
	}

	private static void CreateAccount(User curUser) 
	{
		Scanner inputScan = ScannerSingleton.getScanner();
		
		BankAccountService bankService = BankAccountService.getService();
		
		
		System.out.println();
		System.out.print("Enter a name for the account: ");
		String newBankAccountName = inputScan.next();
		
		bankService.addBankAccount(newBankAccountName, curUser.getUserID());
	}

	private static User register() throws UsernameTakenException 
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

			newUser = userService.register(userNameIn, passwordIn).get();
				
			
			if (newUser != null) 
			{
				break;
				
			}
			
			
		}
		
		return newUser;
		
	}
	
    public static void main( String[] args ) throws Exception
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
    		
    		
    		try {
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
    				try 
    				{
    					register();
					
    				}catch(UsernameTakenException ute) 
    				{
    					System.out.println(ute.getMessage());
					
    				}
    			
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
    		catch(InputMismatchException ime) 
			{
				System.out.println("Invalid choice");
				try {
					ScannerSingleton.killScanner();
				}catch(Exception e) 
				{
					break;
					
				}
				
				
			}
    		
    	}
    	
    	try 
    	{
    		ScannerSingleton.killScanner();
    		
    	}
    	catch(Exception e) 
    	{
    		log.catching(e);
    		log.error("An error occured while closing the input scanner");
    		
    	}
    	    	
    }

	private static void adminDashboard() {
		
		Scanner inputScan = ScannerSingleton.getScanner();
		
		while(true) 
		{
			System.out.println("=====================ADMIN MENU=======================");
			System.out.println("1. View users");
			System.out.println("2. Create user");
			System.out.println("3. Update user's password");
			System.out.println("4. Update user's username");
			System.out.println("5. Delete user");
			System.out.println("6. Logout");
			
			try {
				if (inputScan.nextInt() == 1) 
				{
					ViewUsers();
				}
				else if (inputScan.nextInt() == 2) 
				{
					try 
					{
						register();
					
					}
					catch(UsernameTakenException ute) 
					{
						System.out.println(ute.getMessage());
					
					}
				
				}
				else if (inputScan.nextInt() == 3) 
				{
					UpdateUserPassword();
				}
				else if (inputScan.nextInt() == 4) 
				{
					try 
					{
						UpdateUsersUserName();
					
					}
					catch(UsernameTakenException ute) 
					{
						System.out.println(ute.getMessage());
					
					}
				
				}
				else if (inputScan.nextInt() == 5) 
				{
					DeleteUser();		
				}
				else if (inputScan.nextInt() == 6) 
				{
					System.out.println("Logging off of superuser account");
					break;
				}
			}
			catch(InputMismatchException ime) 
			{
				System.out.println("Invalid choice");
			}
		}	
	}


	private static void UpdateUserPassword() 
	{
		UserService serv = UserService.getService();
		
		Scanner inputScan = ScannerSingleton.getScanner();
		
		System.out.print("Enter username of user to update password: ");
		
		String username = inputScan.next();
		
		System.out.print("Enter new password: ");
		
		String pwordFirstAttmpt = inputScan.next();
		
		System.out.print("Confirm password: ");
		
		String pwordSecondAttmpt = inputScan.next();
		
		if (pwordFirstAttmpt.equals(pwordSecondAttmpt)) 
		{
			serv.updateUserPassword(username, pwordSecondAttmpt);
			
		}
		else 
		{
			System.out.println("Passwords didn't match, please try again");
		}
		
	}
	
	private static void UpdateUsersUserName() 
	{
		UserService serv = UserService.getService();
		
		Scanner inputScan = ScannerSingleton.getScanner();
		
		System.out.print("Enter username of user to update: ");
		
		String username = inputScan.next();
		
		System.out.print("Enter new username: ");
		
		String newUsername = inputScan.next();

		serv.updateUserName(username, newUsername);
		
	}


	private static void DeleteUser() 
	{
		Scanner inputScan = ScannerSingleton.getScanner();
		
		UserService serv = UserService.getService();
		
		System.out.print("Enter username of user to delete: ");
		
		String username = inputScan.next();
		
		serv.deleteUser(username);
		
	}


	private static void ViewUsers() 
	{
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

