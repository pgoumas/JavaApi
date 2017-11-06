package com.pg.bankservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class BankThreadSafe {
   
	// map from account number to balance
    private static Map<String, Integer> accounts = new ConcurrentHashMap<String, Integer>();

	private File file = new File("Accounts.dat");
	
	BankThreadSafe(){
		//initialize class
		   if (!file.exists()) {  
			   saveAccountList(accounts);
	         }
		   else {
			   //accounts = readAccountList();
		   }
	}
  

    public Boolean transfer(String sourceAccount, String destinationAccount ,int sum) throws IllegalArgumentException {
    	Boolean result = false;
        if (sum < 0) {
            throw new IllegalArgumentException("sum cannot be negative");
        }
        synchronized (accounts) {
             accounts.put(sourceAccount, accounts.get(sourceAccount) - sum);
             accounts.put(destinationAccount, accounts.get(destinationAccount) + sum);
             result = true;
        }
        return result;
    }

    public int deposit(String account, int sum) throws IllegalArgumentException {
    	int result = -1;
        if (sum < 0) {
            throw new IllegalArgumentException("sum cannot be negative");
        }
        synchronized (accounts) {
            int balance = 0;
            balance = accounts.get(account);
            result = accounts.put(account, balance + sum);
            if (saveAccountList(accounts)) {
            	return result;
            }
            else {
            	return -1;
            }
        }
    }

    public int balance(String account) {
    	int result = -1;
    	   synchronized (accounts) {
    			Boolean accountExists = this.AccountExists(account);
    			if (accountExists) {
    				result = accounts.get(account);
    			}
    			else {
    				
    			}
               return result;
           }
    }
    
    public Map<String, Integer> getAllAccounts() {
    	synchronized(accounts) {
             return accounts;
    	}
    }
    
    public Boolean AccountExists(String account) {
    	synchronized(accounts) {
             Boolean result = accounts.containsKey(account.intern());
             return result;
    	}
    }
    
    public Boolean addAccount(String account) {
    	synchronized(accounts) {
             accounts.put(account.intern(),0);
             Boolean result = accounts.containsKey(account.intern());
             if (result) {
            	 return saveAccountList(accounts);
             }
             else {
             return false;
             }
    	}
    }
 
    public int withdraw(String account, int sum) {
        synchronized (account.intern()) {
            if (sum > accounts.get(account)) {
                return -1;
            }
            accounts.put(account, accounts.get(account) - sum);
            return sum;
        }
    }
    
    @SuppressWarnings("unchecked")
	private Map<String, Integer> readAccountList()
    {
  	  Map<String, Integer> accountsonFile = new ConcurrentHashMap<String, Integer>();

    	try {
			   FileInputStream f = new FileInputStream(file);  
			   ObjectInputStream s = new ObjectInputStream(f);  
			   accountsonFile = (Map<String,Integer>)s.readObject();         
			   s.close();
			}
			catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	return accountsonFile;
    }
  
	private Boolean saveAccountList(Map<String, Integer> accountList) {
	       
		boolean result = true;
	    	  FileOutputStream f;
	  		try {
	  			f = new FileOutputStream(file);
	  			ObjectOutputStream s;
	  			s = new ObjectOutputStream(f);
	  			s.writeObject(accounts);
	  			s.flush();
	  			s.close();
	      } catch (FileNotFoundException e) { 
	         e.printStackTrace(); 
	      } catch (IOException e) { 
	         e.printStackTrace(); 
	      } 
	  		return result;
	   }   
}