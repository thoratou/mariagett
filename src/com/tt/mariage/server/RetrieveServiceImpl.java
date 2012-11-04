package com.tt.mariage.server;

import java.util.ArrayList;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tt.mariage.client.data.Person;
import com.tt.mariage.client.services.RetrieveService;
import com.tt.mariage.client.services.UserData;

public class RetrieveServiceImpl extends RemoteServiceServlet implements
    RetrieveService {

	private static final long serialVersionUID = 405169472121812429L;

	@Override
	public UserData retrieve(String mail) {
		UserData userData = new UserData();
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
	    
	    return userData;
	}
}