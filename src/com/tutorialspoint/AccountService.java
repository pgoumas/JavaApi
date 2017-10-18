package com.tutorialspoint;  

import java.util.List; 
import javax.ws.rs.GET; 
import javax.ws.rs.Path; 
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType;  
@Path("/AccountService") 

public class AccountService {  
   AccountDao accountDao = new AccountDao();  
   @GET 
   @Path("/accounts") 
   @Produces(MediaType.APPLICATION_XML) 
   public List<Account> getUsers(){ 
      return accountDao.getAllAccounts(); 
   }  
  
}