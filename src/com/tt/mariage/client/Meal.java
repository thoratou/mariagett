package com.tt.mariage.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
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

public class Meal extends PersonCellTable{

	private List<PersonChange<?>> pendingChanges;
    
	private CellTable<Person> personCellTable;
	
	private PersonTableHandler personTableHandler;
    
	public Meal(PersonTableHandler personTableHandler) {
	    this.personTableHandler = personTableHandler;
	    pendingChanges = new ArrayList<PersonChange<?>>();

	    personCellTable = new CellTable<Person>(Person.KEY_PROVIDER);
	    
		personTableHandler.addDataDisplay(personCellTable);
	}

	public void setPanel(VerticalPanel informationPanel) {
		Label generalInfoLabel = new Label();
		generalInfoLabel.setStyleName("h1");
		generalInfoLabel.setText("Meal");

		createNameColumn();
		createFirstNameColumn();
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		createButtons(buttonPanel);
        
		informationPanel.add(generalInfoLabel);
		informationPanel.add(personCellTable);
		informationPanel.add(buttonPanel);
		
	}
	
	private void createButtons(HorizontalPanel buttonPanel) {
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
	    
	    buttonPanel.add(commitButton);
	}

	private void createNameColumn(){
	    // name cell
		addColumn(personCellTable, new TextCell(), "Name", new GetValue<String>() {
			@Override
			public String getValue(Person person) {
				return person.getName();
			}
		}, null);
	}
	
	private void createFirstNameColumn(){
	    // first name cell
        addColumn(personCellTable, new TextCell(), "FirstName", new GetValue<String>() {
        	@Override
        	public String getValue(Person person) {
        		return person.getFirstname();
        	}
        }, null);
	}
}
