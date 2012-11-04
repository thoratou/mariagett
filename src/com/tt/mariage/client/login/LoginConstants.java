package com.tt.mariage.client.login;

import com.google.gwt.i18n.client.Constants;

public interface LoginConstants extends Constants {
	String headerText();
	
	String userMessageText();
	
	String mailText();
	String passwordText();
	String loginButton();
	String registerButton();
	
	String generalFailureMessage();
	
	String registeryInProgressMessage();
	String registeryDoneFirstPart();
	String registeryDoneSecondPart();
	
	String logginMissingMailMessage();
	String logginMissingPasswordMessage();
	String logginInvalidMailMessage();
	String logginInvalidPasswordMessage();
}
