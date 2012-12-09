package com.tt.mariage.client.logistic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
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
	private LogisticConstants logisticConstants = GWT.create(LogisticConstants.class);
	
    final HTML saveMessage = new HTML();
    
	final FlexTable layout = new FlexTable();
	
	final RadioButton hotelYes = new RadioButton("Hotel", logisticConstants.yesText());
	final RadioButton hotelNo = new RadioButton("Hotel", logisticConstants.noText());
	final RadioButton carYes = new RadioButton("Car", logisticConstants.yesText());
	final RadioButton carNo = new RadioButton("Car", logisticConstants.noText());
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
	    layout.setHTML(0, 0, "<b>"+logisticConstants.headerText()+"</b>");
	    
		/// Hotel line
		HTML hotelQuestion = new HTML(logisticConstants.hotelQuestion());

		HorizontalPanel hotelLine =  new HorizontalPanel();
		hotelNo.setValue(true);
		hotelLine.add(hotelYes);
		hotelLine.add(hotelNo);
	    
		layout.setWidget(1, 0, hotelQuestion);
	    layout.setWidget(1, 1, hotelLine);
	    
	    ///Date question
	    HTML dateQuestion = new HTML(logisticConstants.dateQuestion());
	    layout.setWidget(2, 0, dateQuestion);
	    layout.getFlexCellFormatter().setColSpan(2, 0, 2);
		
	    /// Arrival DateBox
	    @SuppressWarnings("deprecation")
		DateTimeFormat dateFormat = DateTimeFormat.getMediumDateFormat();
	    DateBox arrivalDateBox = new DateBox();
	    arrivalDateBox.setFormat(new DateBox.DefaultFormat(dateFormat));

		layout.setWidget(3, 0, new HTML(logisticConstants.arrivalDateText()));	
	    layout.setWidget(3, 1, arrivalDateBox);	

	    /// Departure DateBox
	    DateBox departureDateBox = new DateBox();
	    departureDateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
	    
		layout.setWidget(4, 0, new HTML(logisticConstants.departureDateText()));	
	    layout.setWidget(4, 1, departureDateBox);
	    
	    ///br
	    layout.setWidget(5, 0, new HTML("<br/><br/>"));
	    
	    /// Car line
	    HTML carQuestion = new HTML(logisticConstants.carQuestion());

		HorizontalPanel carLine =  new HorizontalPanel();
		carNo.setValue(true);
		carLine.add(carYes);
		carLine.add(carNo);
	    
		layout.setWidget(6, 0, carQuestion);
	    layout.setWidget(6, 1, carLine);
				
	    HTML carDetailsQuestion = new HTML(logisticConstants.freePlacesQuestion());

		HorizontalPanel carDetails =  new HorizontalPanel();
		carDetails.add(carFreePlaceNumber);

		layout.setWidget(7, 0, carDetailsQuestion);	
	    layout.setWidget(7, 1, carDetails);	
	    
	    layout.setWidget(8, 0, saveMessage);
	    layout.getFlexCellFormatter().setColSpan(8, 0, 2);
	    layout.getRowFormatter().setVisible(8, false);
	    
		HorizontalPanel buttonPanel = new HorizontalPanel();
		createButtons(buttonPanel);
	    layout.setWidget(9, 0, buttonPanel);
	    layout.getFlexCellFormatter().setColSpan(9, 0, 2);
	    
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
	    commitButton.setText(logisticConstants.saveButtonText());
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
						saveMessage.setHTML("<font color=red>"+logisticConstants.saveKOMessage()+"</font>");
					    layout.getRowFormatter().setVisible(4, true);
					}
					
					@Override
					public void onSuccess(SaveData result) {
						if(result.getStatus() == Status.SaveOK) {
							saveMessage.setHTML("<font color=green>"+logisticConstants.saveOKMessage()+"</font>");
							saveMessage.setVisible(true);
						}else if(result.getStatus() == Status.InternalError || result.getStatus() == Status.Undef) {
							saveMessage.setHTML("<font color=red>"+logisticConstants.saveKOMessage()+"<br/>"+result.getMessage()+"</font>");
							saveMessage.setVisible(true);
	    				}
					}
				});
	    	}
	    });
	    
	    buttonPanel.add(commitButton);
	}

}
