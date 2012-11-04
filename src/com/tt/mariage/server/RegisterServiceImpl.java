package com.tt.mariage.server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tt.mariage.client.services.RegisterInfo;
import com.tt.mariage.client.services.RegisterInfo.Status;
import com.tt.mariage.client.services.RegisterService;

public class RegisterServiceImpl extends RemoteServiceServlet implements
    RegisterService {

	private static final long serialVersionUID = 5635171032764951488L;
	
	private static class RandomString
	{

	  private static final char[] symbols = new char[36];

	  static {
	    for (int idx = 0; idx < 10; ++idx)
	      symbols[idx] = (char) ('0' + idx);
	    for (int idx = 10; idx < 36; ++idx)
	      symbols[idx] = (char) ('a' + idx - 10);
	  }

	  private final Random random = new Random();

	  private final char[] buf;

	  public RandomString()
	  {
	    buf = new char[10];
	  }

	  public String nextString()
	  {
	    for (int idx = 0; idx < buf.length; ++idx) 
	      buf[idx] = symbols[random.nextInt(symbols.length)];
	    return new String(buf);
	  }
	}
	
	private RandomString passwordGenerator = new RandomString();
	private Connection connection = null;
	
	private CommonService commonService = new CommonService();

	public RegisterInfo register(String mail) {
		RegisterInfo registerInfo = new RegisterInfo();
	
	    if((mail == null) || ("".equals(mail))){
	    	registerInfo.setStatus(Status.MissingMail);
	    	registerInfo.setMail("");
	    	registerInfo.setMessage("please fill your mail address");  	
	    }
	    else{
	    	registerInfo.setMail(mail);
	    	
	    	String newPassword = passwordGenerator.nextString();
	    	
			try {
				sendMail(mail, newPassword);
				
				updateDatabase(mail, newPassword);
	
				//report sending
		    	registerInfo.setStatus(Status.Registered);
		    	registerInfo.setMessage("mail sent to "+mail+".<br/>please check your mailbox to get the new password.");
		    	
			} catch (AddressException e) {
		    	registerInfo.setStatus(Status.InternalError);
		    	registerInfo.setMessage(commonService.convertException(e));
			} catch (MessagingException e) {
		    	registerInfo.setStatus(Status.InternalError);
		    	registerInfo.setMessage(commonService.convertException(e));
			} catch (ClassNotFoundException e) {
		    	registerInfo.setStatus(Status.InternalError);
		    	registerInfo.setMessage(commonService.convertException(e));
			} catch (SQLException e) {
		    	registerInfo.setStatus(Status.InternalError);
		    	registerInfo.setMessage(commonService.convertException(e));
			} catch (NoSuchAlgorithmException e) {
		    	registerInfo.setStatus(Status.InternalError);
		    	registerInfo.setMessage(commonService.convertException(e));
			} catch (UnsupportedEncodingException e) {
		    	registerInfo.setStatus(Status.InternalError);
		    	registerInfo.setMessage(commonService.convertException(e));
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
			    	registerInfo.setMessage(commonService.convertException(e));
				}
		    }
	    }
	    
	    return registerInfo;
	}
	
	private void sendMail(String mail, String password) throws AddressException, MessagingException{
    	//general settings
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props,
			new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("mariage.thanh.thomas@gmail.com", "04mai2013");
				}
			}
		);
		
		Message message = new MimeMessage(session);
		
		//from
		message.setFrom(new InternetAddress("mariage.thanh.thomas@gmail.com"));
		
		//to
		InternetAddress[] internetAddresses = new InternetAddress[1];
		internetAddresses[0] = new InternetAddress(mail);
		message.setRecipients(Message.RecipientType.TO, internetAddresses);
		
		//content
		message.setSubject("test");
		
		message.setText("password :\n"+password);
		message.setHeader("X-Mailer", "Java");
		message.setSentDate(new Date());
		
		//send message
		Transport.send(message);
	}
	
	private void updateDatabase(String mail, String newPassword) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException {
		Class.forName("org.sqlite.JDBC");
		String home = System.getenv("MARIAGETT_HOME");
		
		connection = DriverManager.getConnection("jdbc:sqlite:"+home+"/mariagett.sqlite");
		Statement statement = connection.createStatement();
		statement.setQueryTimeout(10);
		  
		//check if user exit
		ResultSet rs = statement.executeQuery("select count(*) from USERS where MAIL=\""+mail+"\"");
		int count = 0;
		while(rs.next())
		{
			count = rs.getInt("count(*)");
		}
		rs.close();
		
		MessageDigest digest = MessageDigest.getInstance("SHA-1");
		digest.reset();
        digest.update(newPassword.getBytes());
        byte[] sha1 = digest.digest();
        
		if(count == 0){
			//doesn't exist => create it
	        PreparedStatement createStatement = connection.prepareStatement("insert into USERS values(?, ?, null)");
	        createStatement.setQueryTimeout(10);
	        createStatement.setString(1, mail);
	        createStatement.setBytes(2, sha1);
	        createStatement.executeUpdate();
	        createStatement.close();
		}
		else{
			//exists => update password
	        PreparedStatement updateStatement = connection.prepareStatement("update USERS set PASSWORD=? where MAIL=?");
	        updateStatement.setQueryTimeout(10);
	        updateStatement.setString(2, mail);
	        updateStatement.setBytes(1, sha1);
	        updateStatement.executeUpdate();
	        updateStatement.close();
		}
	}	
}