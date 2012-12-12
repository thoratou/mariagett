package com.tt.mariage.client.data;

import java.util.List;

import com.tt.mariage.client.data.Person.Menu;

public abstract class PersonChange<T> {
    private final Person person;
    private final T value;

    public PersonChange(Person person, T value) {
    	this.person = person;
    	this.value = value;
    }

    public void commit() {
    	doCommit(person, value);
    }

    protected abstract void doCommit(Person person, T value);

    public static PersonChange<String> createNameCommiter(Person person, String name){
    	return new PersonChange<String>(person, name) {
			@Override
			protected void doCommit(Person person, String name) {
				person.setName(name);
			}
		};
    }
    
    public static PersonChange<String> createFirstNameCommiter(Person person, String name){
    	return new PersonChange<String>(person, name) {
			@Override
			protected void doCommit(Person person, String name) {
				person.setFirstname(name);
			}
		};
    }

	public static PersonChange<Boolean> createInfantCommiter(Person person, Boolean value) {
		return new PersonChange<Boolean>(person, value) {
			@Override
			protected void doCommit(Person person, Boolean value) {
				person.setInfant(value.booleanValue());
			}
		};
	}
	
	public static PersonChange<String> createMenuCommiter(Person person, String menu, final List<String> menuList) {
		return new PersonChange<String>(person, menu) {
			@Override
			protected void doCommit(Person person, String value) {
        		int menu = menuList.indexOf(value);
				person.setMenu(Menu.values()[menu]);
			}
		};
	}
}

