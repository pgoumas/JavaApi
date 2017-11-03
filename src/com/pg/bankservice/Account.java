package com.pg.bankservice;  

import java.io.Serializable;  
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 
/**
 * @author pgoumas
 *
 */
@XmlRootElement(name = "account") 
public class Account implements Serializable {  
   private static final long serialVersionUID = 1L; 
   private int id; 
   private String name; 
   private int balance;  
   public Account(){} 
    
   /**
 * @param id
 * @param name
 * @param balance
 */
public Account(int id, String name, int balance){  
      this.id = id; 
      this.name = name; 
      this.balance = balance; 
   }  

   /**
 * @return
 */
public int getId() { 
      return id; 
   }  

   /**
 * @param id
 */
@XmlElement 
   public void setId(int id) { 
      this.id = id; 
   } 

/**
 * @return
 */
public String getName() { 
      return name; 
   } 

/**
 * @param name
 */
@XmlElement
   public void setName(String name) { 
      this.name = name; 
   } 
   public int getBalance() { 
      return balance; 
   } 

/**
 * @param balance
 */
@XmlElement 
   public void setBalance(int balance) { 
      this.balance = balance; 
   }   
} 