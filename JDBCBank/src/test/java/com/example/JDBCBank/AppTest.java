package com.example.JDBCBank;

//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import com.JDBC.Exceptions.NegativeDepositException;
import com.JDBC.Exceptions.UsernameTakenException;
import com.JDBC.model.User;
import com.JDBC.service.BankAccountService;
import com.JDBC.service.UserService;




/**
 * Unit tests for JDBC Bank.
 */
public class AppTest
{
    long testUserId;
	
    long depositAccntId;
    
    long withdrawlAccntId;
	
	@BeforeClass
    public void setup() 
    {
        UserService userServ = UserService.getService();
        
        BankAccountService bankServ = BankAccountService.getService();
        
        try 
        {
        	long userid = userServ.register("test", "testingisezypzy").get().getUserID();
        	
        	long deleteTestUser = userServ.register("deleteme", "1234").get().getUserID();
        	
        	long depositId = depositAccntId = bankServ.addBankAccount("testbankdeposit", userid).get().getAccountID();
        	
        	long withdrawlId = withdrawlAccntId = bankServ.addBankAccount("testbankwithdrawl", userid).get().getAccountID();
        	
        	bankServ.addBankAccount("testdeletebankaccount", userid);
        	
        	bankServ.depositIntoBankAccount(555, withdrawlId);
        	 
        }catch (UsernameTakenException e) {	
			e.printStackTrace();
		} catch (NegativeDepositException e) {
			e.printStackTrace();
		}
        
    }
	
	@AfterClass
	public void teardown() 
	{
		UserService userServ = UserService.getService();
		
		try 
		{
			userServ.deleteUser("test");
			
		}catch(Exception e) 
		{
			
			
		}
		
	}
	
	@Test
	public void testDepositShouldReturnNonZero() 
	{
		//UserService userService = UserService.getService();
		
		BankAccountService bankServ = BankAccountService.getService();
		
		//bankServ.depositIntoBankAccount(100.0, depositAccntId);
		
		//assertNotEquals(0.0,));
		
	}
	
	@Test
	public void testUserRegister() 
	{
		UserService userServ = UserService.getService();
		
		User user;
		
		try 
		{
			user = userServ.register("registerTest", "test").get();
			
		}catch(UsernameTakenException e) 
		{
			throw new AssertionError("There is an existing registerTest user. Did teardown run properly?");
		}
		
		
		assertNotEquals(Optional.empty(), userServ.getUser(user.getUserID()));
		
		
		
		
		
		
	}
	
	
	
	
	
}