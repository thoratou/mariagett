package com.tt.mariage.server;

import java.util.Iterator;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.tt.mariage.client.data.Person;
import com.tt.mariage.client.services.SaveData;
import com.tt.mariage.client.services.SaveData.Status;
import com.tt.mariage.client.services.SaveService;
import com.tt.mariage.client.services.UserData;

public class SaveServiceImpl extends RemoteServiceServlet implements
    SaveService {

	private static final long serialVersionUID = 405169472121812429L;

	@Override
	public SaveData save(UserData userData) {
		SaveData saveData = new SaveData();
		saveData.setStatus(Status.InternalError);
		saveData.setMessage(debug(userData));
		return saveData;
	}

	private String debug(UserData userData) {
		String string = "mail : " + userData.getMail() + "<br/>";

		Iterator<Person> iterator = userData.getPersonList().iterator();
		while(iterator.hasNext()){
			Person person = iterator.next();
			string += "[" + person.getName() + " , " + person.getFirstname() + " , " + (person.isInfant() ? "true" : "false") + " , " + person.getMenu() + "]<br/>";
		}
		
		string += "hotel booking : " + (userData.isWantHotelBooking() ? "true" : "false") + "<br/>";
		string += "has car : " + (userData.isHasCar() ? "true" : "false") + "<br/>";
		string += "free places : " + userData.getFreePlaces() + "<br/>";
		string += "phone number : " + userData.getPhoneNumber() + "<br/>";
		string += "other info : " + userData.getOtherInfo() + "<br/>";
		
		return string;
	}
}