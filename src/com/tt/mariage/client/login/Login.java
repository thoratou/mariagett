package com.tt.mariage.client.login;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tt.mariage.client.services.LoginInfo;
import com.tt.mariage.client.services.LoginInfo.Status;
import com.tt.mariage.client.services.LoginService;
import com.tt.mariage.client.services.LoginServiceAsync;
import com.tt.mariage.client.services.RegisterInfo;
import com.tt.mariage.client.services.RegisterService;
import com.tt.mariage.client.services.RegisterServiceAsync;

public class Login {
	public interface ImageResources extends ClientBundle {	 
	    public static final ImageResources INSTANCE = GWT.create(ImageResources.class);
	 
	    @Source("france.png")
	    ImageResource france();
	 
	    @Source("united-kingdom.png")
	    ImageResource unitedKingdom();
	}
	
	LoginServiceAsync loginService = GWT.create(LoginService.class);
	RegisterServiceAsync registerService = GWT.create(RegisterService.class);
	LoginConstants loginConstants = GWT.create(LoginConstants.class);
	
	public void login(){
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText(loginConstants.headerText());
	    dialogBox.setGlassEnabled(true);
	    dialogBox.setAnimationEnabled(true);
	    
	    VerticalPanel loginPanel = new VerticalPanel();
	    
	    final TextBox userInput = new TextBox();
	    final PasswordTextBox pwdInput = new PasswordTextBox();
	    
		final FlexTable userMessageLayout = new FlexTable();
		{
			userMessageLayout.setCellSpacing(6);
		    FlexCellFormatter cellFormatter = userMessageLayout.getFlexCellFormatter();
		    cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		}
		
		HTML userMessageLabel = new HTML();
		userMessageLabel.setHTML(loginConstants.userMessageText());
		userMessageLayout.setWidget(0, 0, userMessageLabel);
		
		final FlexTable loginLayout = new FlexTable();
		{
		    loginLayout.setCellSpacing(6);
		    FlexCellFormatter cellFormatter = loginLayout.getFlexCellFormatter();
		    cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		}
		
	    loginLayout.setWidget(0, 0, new HTML("<b>"+loginConstants.mailText()+" : </b>"));
	    loginLayout.setWidget(0, 1, userInput);
	    loginLayout.setWidget(1, 0, new HTML("<b>"+loginConstants.passwordText()+" : </b>"));
	    loginLayout.setWidget(1, 1, pwdInput);
	    final HTML messageLabel = new HTML();
	    loginLayout.setWidget(2, 0, messageLabel);
	    loginLayout.getFlexCellFormatter().setColSpan(2, 0, 2);
	    loginLayout.getRowFormatter().setVisible(2, false);
	    
	    //buttons
		FlexTable buttonLayout = new FlexTable();
		{
			buttonLayout.setCellSpacing(6);
		    FlexCellFormatter cellFormatter = buttonLayout.getFlexCellFormatter();
		    cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		}
	    
	    Button loginButton = new Button(loginConstants.loginButton(), new ClickHandler() {
	    	public void onClick(ClickEvent event) {
	    		String user = userInput.getText();
	    		String pwd = pwdInput.getText();
	    		
	    		loginService.login(user, pwd, new AsyncCallback<LoginInfo>() {
	    			public void onFailure(Throwable error) {
	    				messageLabel.setHTML("<font color=red>"+loginConstants.generalFailureMessage()+"</font>");
	    			    loginLayout.getRowFormatter().setVisible(2, true);
	    			}
	    			public void onSuccess(LoginInfo result) {
	    				if(result.getStatus() == Status.LoggedIn) {
	    					dialogBox.hide();
	    				}
	    				else if(result.getStatus() == Status.MissingMail) {
		    				messageLabel.setHTML("<font color=red>"+loginConstants.logginMissingMailMessage()+"</font>");
		    			    loginLayout.getRowFormatter().setVisible(2, true);
	    				}
	    				else if(result.getStatus() == Status.MissingPassword) {
		    				messageLabel.setHTML("<font color=red>"+loginConstants.logginMissingPasswordMessage()+"</font>");
		    			    loginLayout.getRowFormatter().setVisible(2, true);
	    				}
	    				else if(result.getStatus() == Status.InvalidMail) {
		    				messageLabel.setHTML("<font color=red>"+loginConstants.logginInvalidMailMessage()+"</font>");
		    			    loginLayout.getRowFormatter().setVisible(2, true);
	    				}
	    				else if(result.getStatus() == Status.InvalidPassword) {
		    				messageLabel.setHTML("<font color=red>"+loginConstants.logginInvalidPasswordMessage()+"</font>");
		    			    loginLayout.getRowFormatter().setVisible(2, true);
	    				}
	    				else if(result.getStatus() == Status.InternalError || result.getStatus() == Status.Undef) {
		    				messageLabel.setHTML("<font color=red>"+loginConstants.generalFailureMessage()+"<br/>"+result.getMessage()+"</font>");
		    			    loginLayout.getRowFormatter().setVisible(2, true);
	    				}
	    			}
	    		});
	    	}
	    });
	    buttonLayout.setWidget(0, 0, loginButton);
	    
	    Button registerButton = new Button(loginConstants.registerButton(), new ClickHandler() {
	    	public void onClick(ClickEvent event) {
				messageLabel.setHTML("<font color=blue>"+loginConstants.registeryInProgressMessage()+"</font>");
			    loginLayout.getRowFormatter().setVisible(2, true);	    		
	    		
	    		String user = userInput.getText();
	    		registerService.register(user, new AsyncCallback<RegisterInfo>() {
	    			public void onFailure(Throwable error) {
	    				messageLabel.setHTML("<font color=red>"+loginConstants.generalFailureMessage()+"</font>");
	    			    loginLayout.getRowFormatter().setVisible(2, true);
	    			}
	    			public void onSuccess(RegisterInfo result) {
	    				if(result.isRegistered()) {
		    				messageLabel.setHTML(	"<font color=green>"+
		    										loginConstants.registeryDoneFirstPart()+" "+result.getMail()+"<br/>"+
		    										loginConstants.registeryDoneSecondPart()+
		    										"</font>");
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
	    
	    //language
		FlexTable langLayout = new FlexTable();
		{
		    loginLayout.setCellSpacing(6);
		    FlexCellFormatter cellFormatter = loginLayout.getFlexCellFormatter();
		    cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
		}
		
		Image frImage = new Image(ImageResources.INSTANCE.france());
		frImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String url = Window.Location.getProtocol() + "//" + Window.Location.getHost() + "/index_fr.html";
				if (!GWT.isProdMode()) {
					url += "?gwt.codesvr=" + Window.Location.getParameter("gwt.codesvr");
				}
				Window.Location.assign(url);
			}
		});


		Image ukImage = new Image(ImageResources.INSTANCE.unitedKingdom());
		ukImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String url = Window.Location.getProtocol() + "//" + Window.Location.getHost() + "/index.html";
				if (!GWT.isProdMode()) {
					url += "?gwt.codesvr=" + Window.Location.getParameter("gwt.codesvr");
				}
				Window.Location.assign(url);
			}
		});
		
		langLayout.setWidget(0, 0, frImage);
		langLayout.setWidget(0, 1, ukImage);
		
	    loginPanel.add(userMessageLayout);
	    loginPanel.add(loginLayout);
	    loginPanel.add(buttonLayout);
	    loginPanel.add(new HTML("<br/>"));
	    loginPanel.add(langLayout);
	    
	    dialogBox.add(loginPanel);
	    
	    dialogBox.center();
	    dialogBox.hide();
        
        RootPanel.get().add(dialogBox);
        
        dialogBox.show();
	}
}
