package com.tutorialspoint;  

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path; 
import javax.ws.rs.Produces; 
import javax.ws.rs.core.MediaType;  

@Path("/MyService") 

public class MyService {
	
	BankThreadSafe bts = new BankThreadSafe();   
	
	@GET
	@Path("/") 
	@Produces(MediaType.APPLICATION_JSON) 
	public List<String> Index(){
		   
		List<String> result = new ArrayList<String>();
		
			result.add("getAllAccounts");
			result.add("addAccount");
		
		return result;
	   }
	 
	@GET 
	@Path("/getAllAccounts") 
	@Produces(MediaType.APPLICATION_JSON) 
	public Map<String, Integer> getAllAccounts(){ 
	   return bts.getAllAccounts();
   }  
   
   @POST 
   @Path("/addAccount") 
   @Produces(MediaType.APPLICATION_JSON) 
   	public List<String> addAccount(String accountName)
   	{
	   
	List<String> result = new ArrayList<String>();
	
	
	
	//String accountName = request.getParameter("accountName");
	
	Boolean accountExists = bts.AccountExists(accountName);
	if (!accountExists) {
		if (bts.addAccount(accountName)) {
			result.add("account ("+accountName+") created!");
		}
		else {
			result.add("account ("+accountName+") error!");
		}
	}
	else {
		result.add("account ("+accountName+") exists!");
	}
	
	return result;
   }
   
}