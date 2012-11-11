package com.tt.mariage.client.meal;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.tt.mariage.client.PersonCellTable;
import com.tt.mariage.client.data.Person;
import com.tt.mariage.client.data.PersonChange;
import com.tt.mariage.client.data.PersonTableHandler;
import com.tt.mariage.client.data.UserDataHandler;
import com.tt.mariage.client.services.SaveData;
import com.tt.mariage.client.services.SaveData.Status;
import com.tt.mariage.client.services.SaveService;
import com.tt.mariage.client.services.SaveServiceAsync;

public class Meal extends PersonCellTable{

	private List<PersonChange<?>> pendingChanges;
    
	private CellTable<Person> personCellTable;
	
	private PersonTableHandler personTableHandler;

	private final UserDataHandler userDataHandler;
	
	private SaveServiceAsync saveService = GWT.create(SaveService.class);
	private MealConstants mealConstants = GWT.create(MealConstants.class);
	
    final HTML saveMessage = new HTML();
    
	public Meal(UserDataHandler userDataHandler, PersonTableHandler personTableHandler) {
	    this.userDataHandler = userDataHandler;
		this.personTableHandler = personTableHandler;
	    pendingChanges = new ArrayList<PersonChange<?>>();

	    personCellTable = new CellTable<Person>(Person.KEY_PROVIDER);
	    
		personTableHandler.addDataDisplay(personCellTable);
	}

	public void setPanel(Panel informationPanel) {
		createNameColumn();
		createFirstNameColumn();
		createMealColumn();
		
	    saveMessage.setVisible(false);
		
		HorizontalPanel buttonPanel = new HorizontalPanel();
		createButtons(buttonPanel);
		
		FlexTable layout = new FlexTable();
	    layout.setCellSpacing(6);
	    FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
	    
	    cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
	    cellFormatter.setColSpan(0, 0, 2);

	    // Add a title to the form
	    //layout.setWidget(0, 0, headerLabel);
	    layout.setHTML(0, 0, "<b>"+mealConstants.headerText()+"</b>");
	    
	    // Add some standard form options
	    layout.setWidget(1, 0, personCellTable);
	    layout.setWidget(2, 0, saveMessage);	    
	    layout.setWidget(3, 0, buttonPanel);
		
		DecoratorPanel panel = new DecoratorPanel();
		panel.setWidth("100%");
		panel.setWidget(layout);
        
		informationPanel.add(panel);
	}
	
	private void createButtons(HorizontalPanel buttonPanel) {
	    //commit button
	    Button commitButton = new Button();
	    commitButton.setText(mealConstants.saveButtonText());
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
	    		
	    		personTableHandler.saveIntoUserData(userDataHandler.getUserData());
	    		
	    		saveService.save(userDataHandler.getUserData(), new AsyncCallback<SaveData>(){
					
					@Override
					public void onFailure(Throwable caught) {
						saveMessage.setHTML("<font color=red>"+mealConstants.saveKOMessage()+"</font>");
						saveMessage.setVisible(true);
					}
					
					@Override
					public void onSuccess(SaveData result) {
						if(result.getStatus() == Status.SaveOK) {
							saveMessage.setHTML("<font color=green>"+mealConstants.saveOKMessage()+"</font>");
							saveMessage.setVisible(true);
						}else if(result.getStatus() == Status.InternalError || result.getStatus() == Status.Undef) {
							saveMessage.setHTML("<font color=red>"+mealConstants.saveKOMessage()+"<br/>"+result.getMessage()+"</font>");
							saveMessage.setVisible(true);
	    				}
					}
				});
	    	}
	    });
	    
	    buttonPanel.add(commitButton);
	}

	private void createNameColumn(){
	    // name cell
		addColumn(personCellTable, new TextCell(), mealConstants.lastNameText(), new GetValue<String>() {
			@Override
			public String getValue(Person person) {
				return person.getName();
			}
		}, null);
	}
	
	private void createFirstNameColumn(){
	    // first name cell
        addColumn(personCellTable, new TextCell(), mealConstants.firstNameText(), new GetValue<String>() {
        	@Override
        	public String getValue(Person person) {
        		return person.getFirstname();
        	}
        }, null);
	}
	
	private void createMealColumn() {
		//meal choice cell
		List<String> meals = new ArrayList<String>();
		meals.add("");
		meals.add(mealConstants.fishMenuText());
		meals.add(mealConstants.meatMenuText());
		meals.add(mealConstants.childMenuText());
        addColumn(personCellTable, new SelectionCell(meals), mealConstants.mealText(), new GetValue<String>() {
        	@Override
        	public String getValue(Person person) {
        		return person.getMenu();
        	}
        }, new FieldUpdater<Person, String>() {
        	@Override
        	public void update(int index, Person person, String value) {
        		pendingChanges.add(PersonChange.createMenuCommiter(person, value));
        	}
        });
	}

}
