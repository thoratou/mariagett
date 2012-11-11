package com.tt.mariage.client.services;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SaveData implements IsSerializable {

	private int status;
	private String message;
	
	public enum Status{
		Undef,
		SaveOK,
		InternalError
	};
	
	public SaveData() {}
	
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
