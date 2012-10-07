package com.tt.mariage.client.services;

import java.io.Serializable;

public class LoginInfo implements Serializable {

	private static final long serialVersionUID = 6063046451147717561L;
	
	public enum Status{
		Undef,
		LoggedIn,
		MissingMail,
		MissingPassword,
		InvalidMail,
		InvalidPassword,
		InternalError
	};

	private Status status = Status.Undef;
	private String mail;
	private String message;

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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
