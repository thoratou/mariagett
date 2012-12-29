package com.tt.mariage.server;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tt.mariage.client.services.LoginInfo;
import com.tt.mariage.client.services.LoginInfo.Status;
import com.tt.mariage.client.services.LoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements
    LoginService {
	
	private Connection connection = null;
	private String currentMail = null;
	private String currentPassword = null;
	
	private CommonService commonService = new CommonService();

	private static final long serialVersionUID = 5635171032764951488L;

	public LoginInfo login(String mail, String pwd) {
		if(mail != null){
			mail = mail.toLowerCase().trim();	
		}
			
		currentMail = mail;
		currentPassword = pwd;
		
		LoginInfo loginInfo = new LoginInfo();
		loginInfo.setMessage("");
		
		try {
			if((mail == null) || ("".equals(mail))){
		    	loginInfo.setStatus(Status.MissingMail);
		    	loginInfo.setMail("");
		    }
		    else {
		    	if((pwd == null) || ("".equals(pwd))){ 
		        	loginInfo.setStatus(Status.MissingPassword);    		
		    	}
		    	else{
		    		retrieveLogin(loginInfo);    		
		    	}
		    }
		} catch (ClassNotFoundException e) {
	    	loginInfo.setStatus(Status.InternalError);
	    	loginInfo.setMessage(commonService.convertException(e));
		} catch (SQLException e) {
	    	loginInfo.setStatus(Status.InternalError);
	    	loginInfo.setMessage(commonService.convertException(e));
		} catch (NoSuchAlgorithmException e) {
	    	loginInfo.setStatus(Status.InternalError);
	    	loginInfo.setMessage(commonService.convertException(e));
		}
	    finally
	    {
			try
			{
				if(connection != null){
					connection.close();
					connection = null;
				}
			}
			catch(SQLException e)
			{
		    	loginInfo.setMessage(commonService.convertException(e));
			}
	    }
	    
	    return loginInfo;
	}

	private void retrieveLogin(LoginInfo loginInfo) throws NoSuchAlgorithmException, SQLException, ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");
		String home = System.getenv("MARIAGETT_HOME");
		
		connection = DriverManager.getConnection("jdbc:sqlite:"+home+"/mariagett.sqlite");
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(10);
		  
		//check if user exit
		ResultSet rs = statement.executeQuery("select PASSWORD, DATAFILE from USERS where MAIL=\""+currentMail+"\"");
		
		boolean found = false;
		byte[] retrievedSHA1 = null;
				
		while(rs.next())
		{
			retrievedSHA1 = rs.getBytes("PASSWORD");
			
			found = true;
		}
		rs.close();
		
		if(!found){
			loginInfo.setStatus(Status.InvalidMail);
		}
		else{
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
	        digest.update(currentPassword.getBytes());
	        byte[] currentSHA1 = digest.digest();
			
			if(!Arrays.equals(currentSHA1,retrievedSHA1)){
				loginInfo.setStatus(Status.InvalidPassword);
			}
			else{
				loginInfo.setStatus(Status.LoggedIn);
			}
		}
	}
}