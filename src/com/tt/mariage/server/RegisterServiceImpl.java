package com.tt.mariage.server;

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

	public RegisterInfo register(String mail) {
		RegisterInfo registerInfo = new RegisterInfo();
	
	    if((mail == null) || ("".equals(mail))){
	    	registerInfo.setRegistered(false);
	    	registerInfo.setMail("");
	    	registerInfo.setMessage("please fill your mail address");  	
	    }
	    else{
	    	registerInfo.setMail(mail);
	    	
	    	String newPassword = passwordGenerator.nextString();
	    	
			try {
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
				
				message.setText("password :\n"+newPassword);
				message.setHeader("X-Mailer", "Java");
				message.setSentDate(new Date());
				
				//send message
				Transport.send(message);
	
				//report sending
		    	registerInfo.setRegistered(true);
		    	registerInfo.setMessage("mail sent to "+mail+".<br/>please check your mailbox to get the new password.");
		    	
			} catch (AddressException e) {
		    	registerInfo.setRegistered(false);
		    	registerInfo.setMessage(convertException(e));
			} catch (MessagingException e) {
		    	registerInfo.setRegistered(false);
		    	registerInfo.setMessage(convertException(e));
			}
	    }
	    
	    return registerInfo;
	}
	
	private String convertException(Exception exception){
		String ret = exception.getMessage()+"<br/>";
		ret+=convertStackTrace(exception.getStackTrace());
		return ret;
	}
	
	private String convertStackTrace(StackTraceElement[] elements){
		String ret = "";
		for(int i=0; i!=elements.length; ++i){
			ret += "[";
			ret += elements[i].getClassName();
			ret += ".";
			ret += elements[i].getMethodName();
			ret += " / ";
			ret += elements[i].getFileName();
			ret += "#";
			ret += elements[i].getLineNumber();
			ret += "]";
			if(i != elements.length)
				ret+= "<br/>";
		}
		return ret;
	}
}