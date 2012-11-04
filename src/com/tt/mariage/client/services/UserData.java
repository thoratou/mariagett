package com.tt.mariage.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.tt.mariage.client.data.Person;

public class UserData implements IsSerializable {

	private String mail;
	private ArrayList<Person> personList;
	
	public UserData() {}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public ArrayList<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(ArrayList<Person> personList) {
		this.personList = personList;
	}
}
