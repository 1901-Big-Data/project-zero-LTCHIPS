package com.example.JDBCBank;

//import junit.framework.Test;
//import junit.framework.TestCase;
//import junit.framework.TestSuite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import com.JDBC.Exceptions.NegativeDepositException;
import com.JDBC.Exceptions.UsernameTakenException;
import com.JDBC.service.BankAccountService;
import com.JDBC.service.UserService;




/**
 * Unit tests for JDBC Bank.
 */
public class AppTest
{
    
	
	
	@BeforeClass
    public void setup() 
    {
        UserService userServ = UserService.getService();
        
        BankAccountService bankServ = BankAccountService.getService();
        
        try 
        {
        	long userid = userServ.register("test", "testingisezypzy").get().getUserID();
        	
        	long deleteTestUser = userServ.register("deleteme", "1234").get().getUserID();
        	
        	long depositId = bankServ.addBankAccount("testbankdeposit", userid).get().getAccountID();
        	
        	long withdrawlId = bankServ.addBankAccount("testbankwithdrawl", userid).get().getAccountID();
        	
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
	
	
	
	
}
