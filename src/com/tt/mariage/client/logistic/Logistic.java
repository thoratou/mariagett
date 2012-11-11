package com.tt.mariage.client.logistic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.tt.mariage.client.data.LoadHandler;
import com.tt.mariage.client.data.UserDataHandler;
import com.tt.mariage.client.services.SaveData;
import com.tt.mariage.client.services.SaveData.Status;
import com.tt.mariage.client.services.SaveService;
import com.tt.mariage.client.services.SaveServiceAsync;
import com.tt.mariage.client.services.UserData;

public class Logistic {

	private final UserDataHandler userDataHandler;
	
	private SaveServiceAsync saveService = GWT.create(SaveService.class);
	
    final HTML saveMessage = new HTML();
    
	final FlexTable layout = new FlexTable();
	
	final RadioButton hotelYes = new RadioButton("Hotel", "Yes");
	final RadioButton hotelNo = new RadioButton("Hotel", "No");
	final RadioButton carYes = new RadioButton("Car", "Yes");
	final RadioButton carNo = new RadioButton("Car", "No");
	final TextBox carFreePlaceNumber = new TextBox();

	public Logistic(UserDataHandler userDataHandler) {
		this.userDataHandler = userDataHandler;
	}

	public void setPanel(Panel logisticPanel) {
	    layout.setCellSpacing(6);
	    FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();
	    
	    cellFormatter.setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_LEFT);
	    cellFormatter.setColSpan(0, 0, 2);
		
	    /// Title
	    layout.setHTML(0, 0, "<b>Logistics</b>");

		/// Hotel line
		Label hotelQuestion = new Label();
		hotelQuestion.setText("Do you want a book in the hotel ?");

		HorizontalPanel hotelLine =  new HorizontalPanel();
		hotelNo.setValue(true);
		hotelLine.add(hotelYes);
		hotelLine.add(hotelNo);
	    
		layout.setWidget(1, 0, hotelQuestion);
	    layout.setWidget(1, 1, hotelLine);
		
		Label carQuestion = new Label();
		carQuestion.setText("Do you have a car for the celebration ?");

		HorizontalPanel carLine =  new HorizontalPanel();
		carNo.setValue(true);
		carLine.add(carYes);
		carLine.add(carNo);
	    
		layout.setWidget(2, 0, carQuestion);
	    layout.setWidget(2, 1, carLine);
				
		Label carDetailsQuestion = new Label();
		carDetailsQuestion.setText("If yes, how many free places do you have ?");

		HorizontalPanel carDetails =  new HorizontalPanel();
		carDetails.add(carFreePlaceNumber);

		layout.setWidget(3, 0, carDetailsQuestion);	
	    layout.setWidget(3, 1, carDetails);	
	    
	    layout.setWidget(4, 0, saveMessage);
	    layout.getFlexCellFormatter().setColSpan(4, 0, 2);
	    layout.getRowFormatter().setVisible(4, false);
	    
		HorizontalPanel buttonPanel = new HorizontalPanel();
		createButtons(buttonPanel);
	    layout.setWidget(5, 0, buttonPanel);
	    layout.getFlexCellFormatter().setColSpan(5, 0, 2);
	    
		DecoratorPanel panel = new DecoratorPanel();
		panel.setWidth("100%");
		panel.setWidget(layout);
		
		///assembly logisticPanel
		logisticPanel.add(panel);
		
		//add load handler to retrieve logistic data from server
		userDataHandler.addLoadHandler(new LoadHandler() {
			
			@Override
			public void load(UserData userData) {
				boolean wantHotelBooking = userData.isWantHotelBooking();
				hotelYes.setValue(wantHotelBooking);
				hotelNo.setValue(!wantHotelBooking);
				
				boolean hasCar = userData.isHasCar();
				carYes.setValue(hasCar);
				carNo.setValue(!hasCar);
				
				carFreePlaceNumber.setValue(userData.getFreePlaces());
			}
		});
	}
	
	private void createButtons(HorizontalPanel buttonPanel) {
	    //commit button
	    Button commitButton = new Button();
	    commitButton.setText("Save changes");
	    commitButton.addClickHandler(new ClickHandler() {
	    	@Override
	    	public void onClick(ClickEvent event) {
	    		
	    		UserData userData = userDataHandler.getUserData();
	    		userData.setWantHotelBooking(hotelYes.getValue().booleanValue());
	    		userData.setHasCar(carYes.getValue().booleanValue());
	    		userData.setFreePlaces(carFreePlaceNumber.getValue());
	    		
	    		saveService.save(userDataHandler.getUserData(), new AsyncCallback<SaveData>(){
					
					@Override
					public void onFailure(Throwable caught) {
						saveMessage.setHTML("<font color=red>save data error</font>");
					    layout.getRowFormatter().setVisible(4, true);
					}
					
					@Override
					public void onSuccess(SaveData result) {
						if(result.getStatus() == Status.SaveOK) {

						}else if(result.getStatus() == Status.InternalError || result.getStatus() == Status.Undef) {
							saveMessage.setHTML(result.getMessage());
						    layout.getRowFormatter().setVisible(4, true);
	    				}
					}
				});
	    	}
	    });
	    
	    buttonPanel.add(commitButton);
	}

}
