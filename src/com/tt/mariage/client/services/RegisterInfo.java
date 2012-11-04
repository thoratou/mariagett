package com.tt.mariage.client.services;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RegisterInfo implements IsSerializable {

	private boolean registered;
	private String mail;
	private String message;
	
	public RegisterInfo() {}

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
