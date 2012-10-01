package com.tt.mariage.client.services;

import java.io.Serializable;

public class LoginInfo implements Serializable {

	private static final long serialVersionUID = 6063046451147717561L;

	private boolean loggedIn = false;
	private String mail;

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
