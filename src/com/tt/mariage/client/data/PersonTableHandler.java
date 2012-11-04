package com.tt.mariage.client.data;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.tt.mariage.client.services.UserData;

public class PersonTableHandler {
	private final ListDataProvider<Person> dataProvider;
	
	public PersonTableHandler() {
		dataProvider = new ListDataProvider<Person>();
	}
	
	public void fillFromUserData(UserData userData){
		ArrayList<Person> personList = userData.getPersonList();
		Iterator<Person> iterator = personList.iterator();
		while (iterator.hasNext()) {
			Person person = iterator.next();
			person.setId(Person.nextId++);
			dataProvider.getList().add(person);
		}
		refreshDisplays();
	}
	
	public void addDataDisplay(HasData<Person> display) {
	    dataProvider.addDataDisplay(display);
    }
	
	public void removePerson(Person person) {
		dataProvider.getList().remove(person);
		refreshDisplays();
	}

	
	public void refreshDisplays() {
	    dataProvider.refresh();
	}
	
	public void addEmptyPerson() {
		Person person = createNewPerson();
		dataProvider.getList().add(person);
		refreshDisplays();
	}
	
	protected Person createNewPerson() {
		return new Person(Person.nextId++);
	}

}
