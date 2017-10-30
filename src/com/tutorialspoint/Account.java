package com.tutorialspoint;  

import java.io.Serializable;  
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 
@XmlRootElement(name = "account") 

public class Account implements Serializable {  
   private static final long serialVersionUID = 1L; 
   private int id; 
   private String name; 
   private int balance;  
   public Account(){} 
    
   public Account(int id, String name, int balance){  
      this.id = id; 
      this.name = name; 
      this.balance = balance; 
   }  
   public int getId() { 
      return id; 
   }  
   @XmlElement 
   public void setId(int id) { 
      this.id = id; 
   } 
   public String getName() { 
      return name; 
   } 
   @XmlElement
   public void setName(String name) { 
      this.name = name; 
   } 
   public int getBalance() { 
      return balance; 
   } 
   @XmlElement 
   public void setBalance(int balance) { 
      this.balance = balance; 
   }   
} 