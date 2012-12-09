package com.tt.mariage.client.services;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.tt.mariage.client.data.Person;

public class UserData implements IsSerializable {

	private String mail;
	private ArrayList<Person> personList;
	private boolean wantHotelBooking;
	private String arrivalDate;
	private String departureDate;
	private boolean hasCar;	
	private String freePlaces;
	private String phoneNumber;
	private String otherInfo;
	
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

	public boolean isWantHotelBooking() {
		return wantHotelBooking;
	}

	public void setWantHotelBooking(boolean wantHotelBooking) {
		this.wantHotelBooking = wantHotelBooking;
	}
	
	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public boolean isHasCar() {
		return hasCar;
	}

	public void setHasCar(boolean hasCar) {
		this.hasCar = hasCar;
	}

	public String getFreePlaces() {
		return freePlaces;
	}

	public void setFreePlaces(String freePlaces) {
		this.freePlaces = freePlaces;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
}
