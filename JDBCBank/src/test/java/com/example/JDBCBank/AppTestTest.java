package com.example.JDBCBank;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.JDBC.Exceptions.NegativeWithdrawlException;
import com.JDBC.model.BankAccount;
import com.JDBC.model.User;
import com.JDBC.service.BankAccountService;
import com.JDBC.service.UserService;
import com.JDBC.util.ConnectionUtility;

public class AppTestTest {

    long testUserId;
	
    static long depositAccntId;
    
    static long withdrawlAccntId;
    
    static long deleteBankAccountId;
	
    public ExpectedException expectedException = ExpectedException.none();

    
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		
		tearDownAfterClass();
		
        UserService userServ = UserService.getService();
        
        BankAccountService bankServ = BankAccountService.getService();
        
        long userid = userServ.register("test", "testingisezypzy").get().getUserID();
        	
        long deleteTestUser = userServ.register("deleteme", "1234").get().getUserID();
        	
        long depositId = depositAccntId = bankServ.addBankAccount("testbankdeposit", userid).get().getAccountID();
        	
        long withdrawlId = withdrawlAccntId = bankServ.addBankAccount("testbankwithdrawl", userid).get().getAccountID();
        
        deleteBankAccountId = bankServ.addBankAccount("deleteBankAccount", userid).get().getAccountID();
        	
        bankServ.addBankAccount("testdeletebankaccount", userid);
        	
        bankServ.depositIntoBankAccount(555, withdrawlId);
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		Connection con = ConnectionUtility.getConnection();
		
		try 
		{
			PreparedStatement ps = con.prepareStatement("DELETE FROM bankaccount WHERE accountname = 'testdeletebankaccount' or accountname = 'testbankdeposit' or accountname = 'testbankwithdrawl' or accountname = 'deleteBankAccount'");
			
			ps.execute();
			
			PreparedStatement ps2 = con.prepareStatement("DELETE FROM users WHERE username = 'test' or username = 'deleteme' or username = 'newguy' or username = 'registerTest'");
				
			ps2.execute();
			
		}catch(Exception e) 
		{
			con.close();
			
		}
		
		
		
	}

	@Test
	public void TestDepositShouldReturnNonZero() 
	{	
		BankAccountService bankServ = BankAccountService.getService();
		
		bankServ.depositIntoBankAccount(256, depositAccntId);
		
		assertEquals(256, Math.round(bankServ.getBankAccount(depositAccntId).get().getBalance()), 0.0000001);
		
	}
	
	@Test
	public void TestWithdrawl() 
	{
		BankAccountService bankServ = BankAccountService.getService();
		
		bankServ.withdrawFromBankAccount(100.0, withdrawlAccntId);
		
		BankAccount bankAccnt = bankServ.getBankAccount("testbankwithdrawl").get();
		
		double newAmount = bankAccnt.getBalance();
		
		assertEquals(455.0, newAmount, 0.00001);
		
	}

	@Test
	public void TestUserRegister() 
	{
		UserService userServ = UserService.getService();
		
		User user;
		
		user = userServ.register("registerTest", "test").get();	
		
		assertNotEquals(Optional.empty(), userServ.getUser(user.getUserID()));
		
	}
	
	@Test
	public void TestNonExistentUserShouldThrow() 
	{
		UserService userServ = UserService.getService();
		
		expectedException.expect(NoSuchElementException.class); 
		
		try 
		{
			User usrTest = userServ.login("Idontexist", "lol").get();
			fail("User existed when it shouldn't have");
		}
		catch(NoSuchElementException nsee) 
		{
			//it failed! WOOOOOOOOOOOOOOOO!
			
		}
	}
	@Test
	public void TestDeleteUser() 
	{
		UserService userServ = UserService.getService();
		
		long delid = userServ.deleteUser("deleteme");
		
		try 
		{
			User usr = userServ.getUser(delid).get();
			fail("User existed when it shouldn't have");
		}
		catch(NoSuchElementException nsee) 
		{
			
		}
		
		
	}
	
	@Test
	public void TestNegativeWithdrawlShouldThrow() 
	{
		BankAccountService bankServ = BankAccountService.getService();
		
		try 
		{
			bankServ.withdrawFromBankAccount(-10, withdrawlAccntId);
			fail("Oh no, a negative withdraw just occured!");
			
		}catch(NegativeWithdrawlException nwe) 
		{
			
			
		}
	}
	
	@Test
	public void TestDeleteBankAccount() 
	{
		BankAccountService bankServ = BankAccountService.getService();
		
		bankServ.deleteBankAccount(deleteBankAccountId);
		
		try 
		{
			BankAccount getBank = bankServ.getBankAccount(deleteBankAccountId).get();
			fail("Bank account survived deletion, failure!");
		}
		catch(NoSuchElementException nsee) 
		{
			
			
		}
		
	}
	
	
	
	

}
