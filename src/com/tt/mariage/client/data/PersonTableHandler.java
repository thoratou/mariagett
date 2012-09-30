package com.tt.mariage.client.data;

import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;

public class PersonTableHandler {
	private final ListDataProvider<Person> dataProvider;
	
	public PersonTableHandler() {
		dataProvider = new ListDataProvider<Person>();
		
		//tmp
		Person toto = createNewPerson();
		toto.setName("AAAA");
		toto.setFirstname("BBBB");
		toto.setInfant(false);

		Person titi = createNewPerson();
		titi.setName("CCCC");
		titi.setFirstname("DDDD");
		titi.setInfant(true);
		
		dataProvider.getList().add(toto);
		dataProvider.getList().add(titi);
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
