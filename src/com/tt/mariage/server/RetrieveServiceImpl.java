package com.tt.mariage.server;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tt.mariage.client.data.Person;
import com.tt.mariage.client.services.RetrieveData;
import com.tt.mariage.client.services.RetrieveData.Status;
import com.tt.mariage.client.services.RetrieveService;
import com.tt.mariage.client.services.UserData;

public class RetrieveServiceImpl extends RemoteServiceServlet implements
    RetrieveService {

	private static final long serialVersionUID = 405169472121812429L;

	@Override
	public RetrieveData retrieve(String mail) {
		RetrieveData retrieveData = new RetrieveData();
		retrieveData.setStatus(Status.Undef);
		retrieveData.setMessage("");
		UserData userData = new UserData();
		userData.setMail(mail);
		retrieveData.setUserData(userData);
		
		userData.setPersonList(new ArrayList<Person>());
		
		Person toto = new Person();
		toto.setName("AAAA");
		toto.setFirstname("BBBB");
		toto.setInfant(false);

		Person titi = new Person();
		titi.setName("CCCC");
		titi.setFirstname("DDDD");
		titi.setInfant(true);
		
		userData.getPersonList().add(toto);
		userData.getPersonList().add(titi);
		
		userData.setWantHotelBooking(true);
		userData.setArrivalDate("");
		userData.setDepartureDate("");
		userData.setHasCar(true);
		userData.setFreePlaces("5");
		userData.setPhoneNumber("0612345678");
		userData.setOtherInfo("qibouyhbvouhdsfbvod");
		
		retrieveData.setStatus(Status.RetrieveOK);
	    
	    return retrieveData;
	}
}