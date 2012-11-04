package com.tt.mariage.client.services;

import com.google.gwt.user.client.rpc.IsSerializable;

public class LoginInfo implements IsSerializable {

	public enum Status{
		Undef,
		LoggedIn,
		MissingMail,
		MissingPassword,
		InvalidMail,
		InvalidPassword,
		InternalError
	};

	private int status;
	private String mail;
	private String message;
	
	public LoginInfo() {}

	public Status getStatus() {
		return Status.values()[status];
	}

	public void setStatus(Status status) {
		this.status = status.ordinal();
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
