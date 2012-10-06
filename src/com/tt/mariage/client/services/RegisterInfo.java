package com.tt.mariage.client.services;

import java.io.Serializable;

public class RegisterInfo implements Serializable {

	private static final long serialVersionUID = 6063046451147717561L;

	private boolean registered = false;
	private String mail;
	private String message;

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
