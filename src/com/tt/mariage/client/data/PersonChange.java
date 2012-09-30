package com.tt.mariage.client.data;

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
}

