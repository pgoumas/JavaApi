package com.pg.bankservice;

import static org.junit.Assert.fail;

/**
 * @author pgoum
 *
 */
public class MyThread extends Thread {
	  
		String account = "";
		int deposit = 0 ;
	
	public MyThread(Object parameter1,Object parameter2) {
	       // store parameter for later user
			account = (String) parameter1;
			deposit = (int) parameter2;
	   }
    
	/* 
	 * @see java.lang.Thread#run()
	 */
	public void run(){
    	
    	BankThreadSafe bts = new BankThreadSafe();
    	Boolean accountExists = bts.AccountExists(account);
		if (!accountExists) {
			Boolean accountCreated = bts.addAccount(account);
			if (!accountCreated) {
				fail("failed to create account despite it did not exist!");
			}
		}
		else {
			//fail("account exists!");
		}
		bts.deposit(account, deposit); 
		//fail("account"+account);
    }
  }
