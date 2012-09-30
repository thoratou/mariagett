package com.tt.mariage.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Login {
	void login(){
		
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Login");
	    dialogBox.setGlassEnabled(true);
	    dialogBox.setAnimationEnabled(true);
	    
	    VerticalPanel loginPanel = new VerticalPanel();
	    
	    //user
	    HorizontalPanel userPanel = new HorizontalPanel();
	    
	    Label userLabel = new Label();
	    userLabel.setText("User :");
	    userPanel.add(userLabel);
	    
	    final TextBox userInput = new TextBox();
	    userPanel.add(userInput);
	    
	    
	    //pwd
	    HorizontalPanel pwdPanel = new HorizontalPanel();
	    
	    Label pwdLabel = new Label();
	    pwdLabel.setText("Password :");
	    pwdPanel.add(pwdLabel);
	    
	    final PasswordTextBox pwdInput = new PasswordTextBox();
	    pwdPanel.add(pwdInput);
	    
	    //buttons
	    HorizontalPanel buttonsPanel = new HorizontalPanel();
	    
	    Button loginButton = new Button("Log in", new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		String user = userInput.getText();
	    		String pwd = pwdInput.getText();
	    		if("test".equals(user) && "test".equals(pwd)){
	    			dialogBox.hide();
	    		}
	    	}
	    });
	    buttonsPanel.add(loginButton);
	    
	    Button registerButton = new Button("Register / Reset Password", new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    	}
	    });
	    buttonsPanel.add(registerButton);

	    
	    loginPanel.add(userPanel);
	    loginPanel.add(pwdPanel);
	    loginPanel.add(buttonsPanel);
	    
	    dialogBox.add(loginPanel);
	    
	    dialogBox.center();
	    dialogBox.hide();
        
        RootPanel.get().add(dialogBox);
        
        dialogBox.show();
	}
}
