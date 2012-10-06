package com.tt.mariage.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tt.mariage.client.services.LoginInfo;
import com.tt.mariage.client.services.LoginService;
import com.tt.mariage.client.services.LoginServiceAsync;
import com.tt.mariage.client.services.RegisterInfo;
import com.tt.mariage.client.services.RegisterService;
import com.tt.mariage.client.services.RegisterServiceAsync;

public class Login {
	LoginServiceAsync loginService = GWT.create(LoginService.class);
	RegisterServiceAsync registerService = GWT.create(RegisterService.class);
	
	void login(){
		
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Login");
	    dialogBox.setGlassEnabled(true);
	    dialogBox.setAnimationEnabled(true);
	    
	    VerticalPanel loginPanel = new VerticalPanel();
	    
	    final TextBox userInput = new TextBox();
	    final PasswordTextBox pwdInput = new PasswordTextBox();
	    
		final FlexTable loginLayout = new FlexTable();
		{
		    loginLayout.setCellSpacing(6);
		    FlexCellFormatter cellFormatter = loginLayout.getFlexCellFormatter();
		    cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		}
		
	    loginLayout.setWidget(0, 0, new HTML("<b>Mail : </b>"));
	    loginLayout.setWidget(0, 1, userInput);
	    loginLayout.setWidget(1, 0, new HTML("<b>Password : </b>"));
	    loginLayout.setWidget(1, 1, pwdInput);
	    final HTML messageLabel = new HTML();
	    loginLayout.setWidget(2, 0, messageLabel);
	    loginLayout.getFlexCellFormatter().setColSpan(2, 0, 2);
	    loginLayout.getRowFormatter().setVisible(2, false);
	    
	    //buttons
		FlexTable buttonLayout = new FlexTable();
		{
		    loginLayout.setCellSpacing(6);
		    FlexCellFormatter cellFormatter = loginLayout.getFlexCellFormatter();
		    cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		}
	    
	    Button loginButton = new Button("Log in", new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		String user = userInput.getText();
	    		String pwd = pwdInput.getText();
	    		
	    		loginService.login(user, pwd, new AsyncCallback<LoginInfo>() {
	    			public void onFailure(Throwable error) {
	    				messageLabel.setHTML("<b>Server error, please retry</b>");
	    			    loginLayout.getRowFormatter().setVisible(2, true);
	    			}
	    			public void onSuccess(LoginInfo result) {
	    				if(result.isLoggedIn()) {
	    					dialogBox.hide();
	    				}
	    			}
	    		});
	    	}
	    });
	    buttonLayout.setWidget(0, 0, loginButton);
	    
	    Button registerButton = new Button("Register / Reset Password", new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		String user = userInput.getText();
	    		
	    		registerService.register(user, new AsyncCallback<RegisterInfo>() {
	    			public void onFailure(Throwable error) {
	    				messageLabel.setHTML("Server error, please retry");
	    			    loginLayout.getRowFormatter().setVisible(2, true);
	    			}
	    			public void onSuccess(RegisterInfo result) {
	    				if(result.isRegistered()) {
		    				messageLabel.setHTML("<font color=green>"+result.getMessage()+"</font>");
		    			    loginLayout.getRowFormatter().setVisible(2, true);
	    				}
	    				else{
		    				messageLabel.setHTML("<font color=red>"+result.getMessage()+"</font>");
		    			    loginLayout.getRowFormatter().setVisible(2, true);	    					
	    				}
	    			}
	    		});
	    	}
	    });
	    
	    buttonLayout.setWidget(0, 1, registerButton);
	    
	    loginPanel.add(loginLayout);
	    loginPanel.add(buttonLayout);
	    
	    dialogBox.add(loginPanel);
	    
	    dialogBox.center();
	    dialogBox.hide();
        
        RootPanel.get().add(dialogBox);
        
        dialogBox.show();
	}
}
