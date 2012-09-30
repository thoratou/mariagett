package com.tt.mariage.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tt.mariage.client.data.Person;
import com.tt.mariage.client.data.PersonChange;
import com.tt.mariage.client.data.PersonTableHandler;

public class Information extends PersonCellTable{

	private List<PersonChange<?>> pendingChanges;
    
	private CellTable<Person> personCellTable;
	
	private PersonTableHandler personTableHandler;
    
	public Information(PersonTableHandler personTableHandler) {
	    this.personTableHandler = personTableHandler;
	    pendingChanges = new ArrayList<PersonChange<?>>();

	    personCellTable = new CellTable<Person>(Person.KEY_PROVIDER);
	    
		personTableHandler.addDataDisplay(personCellTable);
	}

	public void setPanel(VerticalPanel informationPanel) {
		Label generalInfoLabel = new Label();
		generalInfoLabel.setStyleName("h1");
		generalInfoLabel.setText("General Information");

		createNameColumn();
		createFirstNameColumn();
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		createButtons(buttonPanel);
        
		informationPanel.add(generalInfoLabel);
		informationPanel.add(personCellTable);
		informationPanel.add(buttonPanel);
		
	}
	
	private void createButtons(HorizontalPanel buttonPanel) {
	    //add button
	    Button addButton = new Button();
	    addButton.setText("Add");
	    addButton.addClickHandler(new ClickHandler() {
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		// Push the changes to the views.
	    		personTableHandler.addEmptyPerson();
	    	}
	    });
	    
	    //commit button
	    Button commitButton = new Button();
	    commitButton.setText("Save changes");
	    commitButton.addClickHandler(new ClickHandler() {
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		// Commit the changes.
	    		for (PersonChange<?> pendingChange : pendingChanges) {
	    			pendingChange.commit();
	    		}
	    		pendingChanges.clear();

	    		// Push the changes to the views.
	    		personTableHandler.refreshDisplays();
	    	}
	    });
	    
	    buttonPanel.add(addButton);
	    buttonPanel.add(commitButton);
	}

	private void createNameColumn(){
	    // name cell
		addColumn(personCellTable, new EditTextCell(), "Name", new GetValue<String>() {
			@Override
			public String getValue(Person person) {
				return person.getName();
			}
		}, new FieldUpdater<Person, String>() {
			@Override
			public void update(int index, Person person, String value) {
				pendingChanges.add(PersonChange.createNameCommiter(person, value));
			}
		});
	}
	
	private void createFirstNameColumn(){
	    // first name cell
        addColumn(personCellTable, new EditTextCell(), "FirstName", new GetValue<String>() {
        	@Override
        	public String getValue(Person person) {
        		return person.getFirstname();
        	}
        }, new FieldUpdater<Person, String>() {
        	@Override
        	public void update(int index, Person person, String value) {
        		pendingChanges.add(PersonChange.createFirstNameCommiter(person, value));
        	}
        });
	}
}
