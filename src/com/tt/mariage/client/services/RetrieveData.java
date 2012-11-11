package com.tt.mariage.client.services;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RetrieveData implements IsSerializable {

	private UserData userData;
	private int status;
	private String message;
	
	public enum Status{
		Undef,
		RetrieveOK,
		InternalError
	};
	
	public RetrieveData() {}
	
	public UserData getUserData() {
		return userData;
	}
	
	public void setUserData(UserData userData) {
		this.userData = userData;
	}
	
	public Status getStatus() {
		return Status.values()[status];
	}

	public void setStatus(Status status) {
		this.status = status.ordinal();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
