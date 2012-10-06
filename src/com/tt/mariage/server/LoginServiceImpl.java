package com.tt.mariage.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tt.mariage.client.services.LoginInfo;
import com.tt.mariage.client.services.LoginService;

public class LoginServiceImpl extends RemoteServiceServlet implements
    LoginService {

	private static final long serialVersionUID = 5635171032764951488L;

	public LoginInfo login(String mail, String pwd) {
    LoginInfo loginInfo = new LoginInfo();

    if("test".equals(mail) && "test".equals(pwd)){
    	loginInfo.setLoggedIn(true);
    	loginInfo.setMail(mail);
    }
    else{
    	loginInfo.setLoggedIn(false);
    	loginInfo.setMessage("Invalid mail and/or password");
    }
    
    return loginInfo;
  }

}