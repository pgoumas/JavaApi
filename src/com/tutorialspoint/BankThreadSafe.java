package com.tutorialspoint;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public final class BankThreadSafe {
   
	BankThreadSafe(){
		//initialize class
		/* try { 
	         File file = new File("Accounts.dat"); 
	         if (!file.exists()) {  
	            saveAccountList(accounts); 
	         } 
	         else{ 
	        	 Properties properties = new Properties();
	        	 properties.load(new FileInputStream("Accounts.dat"));

	        	 for (String key : properties.stringPropertyNames()) {
	        		 accounts.put(key, Integer.parseUnsignedInt(properties.get(key).toString()));
	        	 }
	         } 
	      } catch (IOException e) { 
	         e.printStackTrace(); 
	      } finally {
		}   
		*/
	}
  
	// map from account number to balance
    private static Map<String, Integer> accounts = new ConcurrentHashMap<String, Integer>();

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
        	return result;
        }
    }

    public int balance(String account) {
    	int result = -1;
    	   synchronized (accounts) {
    			Boolean accountExists = this.AccountExists(account);
    			if (accountExists) {
    				result = accounts.get(account);
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
             return result;
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
    
    @SuppressWarnings("unused")
	private void saveAccountList(Map<String, Integer> accountList) {
	      try { 
	    	 
	    	  Properties properties = new Properties();

	    	  for (Map.Entry<String,Integer> entry : accounts.entrySet()) {
	    	      properties.put(entry.getKey(), entry.getValue());
	    	  }

	    	  properties.store(new FileOutputStream("Accounts.dat"), null);

	      } catch (FileNotFoundException e) { 
	         e.printStackTrace(); 
	      } catch (IOException e) { 
	         e.printStackTrace(); 
	      } 
	   }   
}