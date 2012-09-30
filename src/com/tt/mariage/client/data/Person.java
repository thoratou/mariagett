package com.tt.mariage.client.data;

import com.google.gwt.view.client.ProvidesKey;

public class Person{
	public static int nextId = 0;
	
	public static final ProvidesKey<Person> KEY_PROVIDER = new ProvidesKey<Person>() {
		public Object getKey(Person item) {
			return item == null ? null : item.getId();
		}
	};
	
	private int id;
	private String name;
	private String firstname;
	private boolean isInfant;
	
	public Person(int id) {
		this.id = id;
		name = new String();
		firstname = new String();
		isInfant = false;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public boolean isInfant() {
		return isInfant;
	}

	public void setInfant(boolean isInfant) {
		this.isInfant = isInfant;
	}

    @Override
    public int hashCode() {
      return getId();
    }
    
    public void merge(Person person){
    	name = person.name;
    	firstname = person.firstname;
    	isInfant = person.isInfant;
    }
}
