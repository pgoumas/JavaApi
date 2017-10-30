package com.tutorialspoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
	@SuppressWarnings("unchecked")
	public List<Account> getAllAccounts(){
		   List<Account> accountList = null; 
		      try { 
		         File file = new File("Accounts.dat"); 
		         if (!file.exists()) { 
		            Account account = new Account(1, "100700", 1000); 
		            accountList = new ArrayList<Account>(); 
		            accountList.add(account); 
		            saveAccountList(accountList); 
		         } 
		         else{ 
		            FileInputStream fis = new FileInputStream(file); 
		            ObjectInputStream ois = new ObjectInputStream(fis); 
		            accountList = (List<Account>) ois.readObject(); 
		            ois.close(); 
		         } 
		      } catch (IOException e) { 
		         e.printStackTrace(); 
		      } catch (ClassNotFoundException e) { 
		         e.printStackTrace(); 
		      }   
		      return accountList; 
	}
	
	  private void saveAccountList(List<Account> accountList){ 
	      try { 
	         File file = new File("Accounts.dat"); 
	         FileOutputStream fos;  
	         fos = new FileOutputStream(file); 
	         ObjectOutputStream oos = new ObjectOutputStream(fos); 
	         oos.writeObject(accountList); 
	         oos.close(); 
	      } catch (FileNotFoundException e) { 
	         e.printStackTrace(); 
	      } catch (IOException e) { 
	         e.printStackTrace(); 
	      } 
	   }   
}
