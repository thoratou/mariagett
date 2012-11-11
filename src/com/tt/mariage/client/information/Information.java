package com.tt.mariage.client.information;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
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

public class Information extends PersonCellTable{

	private List<PersonChange<?>> pendingChanges;
    
	private CellTable<Person> personCellTable;
	
	private PersonTableHandler personTableHandler;

	private final UserDataHandler userDataHandler;
	
	private SaveServiceAsync saveService = GWT.create(SaveService.class);
	
    final HTML saveMessage = new HTML();
    
	public Information(UserDataHandler userDataHandler, PersonTableHandler personTableHandler) {
	    this.userDataHandler = userDataHandler;
		this.personTableHandler = personTableHandler;
	    pendingChanges = new ArrayList<PersonChange<?>>();

	    personCellTable = new CellTable<Person>(Person.KEY_PROVIDER);
	    
		personTableHandler.addDataDisplay(personCellTable);
	}

	public void setPanel(Panel informationPanel) {
		
		createNameColumn();
		createFirstNameColumn();
		createInfantColumn();
		createRemoveColumn();
		
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
	    layout.setHTML(0, 0, "<b>General Information</b>");

	    // Add some standard form options
	    layout.setWidget(1, 0, personCellTable);
	    layout.setWidget(2, 0, saveMessage);	    
	    layout.setWidget(3, 0, buttonPanel);
		
		DecoratorPanel panel = new DecoratorPanel();
		panel.setWidth("100%");
		panel.setWidget(layout);
        
		informationPanel.add(panel);
		
		/*informationPanel.add(generalInfoLabel);
		informationPanel.add(personCellTable);
		informationPanel.add(buttonPanel);*/
		
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
	    		
	    		personTableHandler.saveIntoUserData(userDataHandler.getUserData());
	    		
	    		saveService.save(userDataHandler.getUserData(), new AsyncCallback<SaveData>(){
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onSuccess(SaveData result) {
						if(result.getStatus() == Status.SaveOK) {

						}else if(result.getStatus() == Status.InternalError || result.getStatus() == Status.Undef) {
							saveMessage.setHTML(result.getMessage());
							saveMessage.setVisible(true);
	    				}
					}
				});
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
	
	private void createInfantColumn() {
		// is infant checkbox cell
        addColumn(personCellTable, new CheckboxCell(), "Infant", new GetValue<Boolean>() {
        	@Override
        	public Boolean getValue(Person person) {
        		return new Boolean(person.isInfant());
        	}
        }, new FieldUpdater<Person, Boolean>() {
        	@Override
        	public void update(int index, Person person, Boolean value) {
        		pendingChanges.add(PersonChange.createInfantCommiter(person, value));
        	}
        });
	}
	
	private void createRemoveColumn() {
	    // first name cell
        addColumn(personCellTable, new ButtonCell(){
        	@Override
        	public void render(com.google.gwt.cell.client.Cell.Context context, SafeHtml data, SafeHtmlBuilder sb) {
        		sb.appendHtmlConstant("<button type=\"button\" class=\"gwt-Button\" tabindex=\"-1\">");
                if (data != null) {
                  sb.append(data);
                }
                sb.appendHtmlConstant("</button>");
        	}
        }, "Remove", new GetValue<String>() {
        	@Override
        	public String getValue(Person person) {
        		return "Remove";
        	}
        }, new FieldUpdater<Person, String>() {
        	@Override
        	public void update(int index, Person person, String value) {
        		personTableHandler.removePerson(person);
        	}
        });
	}


}
