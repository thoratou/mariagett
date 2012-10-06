package com.tt.mariage.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tt.mariage.client.services.RegisterInfo;
import com.tt.mariage.client.services.RegisterService;

public class RegisterServiceImpl extends RemoteServiceServlet implements
    RegisterService {

	private static final long serialVersionUID = 5635171032764951488L;

	public RegisterInfo register(String mail) {
	RegisterInfo registerInfo = new RegisterInfo();

    if("test".equals(mail)){
    	registerInfo.setRegistered(true);
    	registerInfo.setMail(mail);
    	registerInfo.setMessage("password reset.<br/>mail sent to "+mail+".<br/>please check your mailbox to get the new password.");
    }
    else{
    	registerInfo.setRegistered(false);
    	registerInfo.setMail(mail);
    	registerInfo.setMessage("unable to register");  	
    }
    
    return registerInfo;
  }
}