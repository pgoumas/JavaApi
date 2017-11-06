/**
 * 
 */
package com.pg.bankservice;

import static org.junit.Assert.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 * @author pgoumas
 *
 */
public class TestBankThreadSafe {


	/**
	 * 
	 */
	@Test
	public void test_deposit_on_new_account() {
		BankThreadSafe bts = new BankThreadSafe();
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(100);
		String account = "AC"+randomInt;
		Boolean accountExists = bts.AccountExists(account);
		if (!accountExists) {
			Boolean accountCreated = bts.addAccount(account);
			if (!accountCreated) {
				fail("failed to create account!");
			}
		}
		else {
			fail("account exists!");
		}
		bts.deposit(account, 1000); 
		int balance = bts.balance(account);
		
		assertEquals(balance,1000);
		}
	  
	/**
	 * 
	 */
	@Test
	public void test_deposit_on_multiple_threads() {
		
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(10000);
		String account = "AC"+randomInt;
		
		int deposit1 = 1000;
		int deposit2 = 1000;
		
		BankThreadSafe bts = new BankThreadSafe();
		
		Boolean accountExists = bts.AccountExists(account);
		if (!accountExists) {
			Boolean accountCreated = bts.addAccount(account);
			if (!accountCreated) {
				fail("failed to create account!");
			}
		}
		else {
			fail("account exists!");
		}
		
		int previousBalance = bts.balance(account);
		
		MyThread myThread = new MyThread(account,deposit1);
		myThread.start();
		
		
		MyThread myThread2 = new MyThread(account,deposit2);
		myThread2.start();
		
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int currentbalance = bts.balance(account);
		assertEquals(currentbalance,previousBalance+deposit1+deposit2);
      
      
	}
	
	/**
	 * 
	 */
	@Test	
	public void test_add_account() {
		Boolean result = false;
		BankThreadSafe bts = new BankThreadSafe();
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(10000);
		String account = "AC"+randomInt;
		
		Boolean accountExists = bts.AccountExists(account);
		if (!accountExists) {
			result = bts.addAccount(account);
			assertEquals(result,true);
		}
		else {
			fail("account ("+account+") exists!");
		}
	}

	/**
	 * 
	 */
	@Test	
	public void test_transfer() {
		
		String sourceAccount = "123";
		String destinationAccount = "123456";
		int ammount = 1000;
		
		BankThreadSafe bts = new BankThreadSafe();
		
		Boolean accountExists = bts.AccountExists(sourceAccount);
		if (!accountExists) {
			Boolean accountCreated = bts.addAccount(sourceAccount);
			if (!accountCreated) {
				fail("failed to create account despite it did not exist!");
			}
		}
		else {
			//fail("account exists!");
		}
		
		accountExists = bts.AccountExists(destinationAccount);
		if (!accountExists) {
			Boolean accountCreated = bts.addAccount(destinationAccount);
			if (!accountCreated) {
				fail("failed to create account despite it did not exist!");
			}
		}
		else {
			//fail("account exists!");
		}
		
		int previousBalanceSourceAccount = bts.balance(sourceAccount);
		assertNotEquals(previousBalanceSourceAccount,-1);
		
		int previousBalanceDestinationAccount = bts.balance(destinationAccount);
		assertNotEquals(previousBalanceDestinationAccount,-1);
		
		boolean tranfer_result = bts.transfer(sourceAccount, destinationAccount, ammount);
		assertEquals(true,tranfer_result);
		
		int currentBalanceSourceAccount = bts.balance(sourceAccount);
		assertNotEquals(currentBalanceSourceAccount,-1);
		
		int currentBalanceDestinationAccount = bts.balance(destinationAccount);
		assertNotEquals(currentBalanceDestinationAccount,-1);
		
		assertEquals(currentBalanceSourceAccount,previousBalanceSourceAccount-ammount);
		assertEquals(currentBalanceDestinationAccount,previousBalanceDestinationAccount+ammount);
	
	}

}
