package com.tt.mariage.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Logistic {

	public void setPanel(VerticalPanel logisticPanel) {
		Label logisticLabel = new Label();
		logisticLabel.setStyleName("h1");
		logisticLabel.setText("Logistics");

		/// Hotel line
		HorizontalPanel hotelLine =  new HorizontalPanel();
		Label hotelQuestion = new Label();
		hotelQuestion.setText("Do you want a book in the hotel ?");
		RadioButton hotelYes = new RadioButton("Hotel", "Yes");
		RadioButton hotelNo = new RadioButton("Hotel", "No");
		hotelNo.setValue(true);
		hotelLine.add(hotelQuestion);
		hotelLine.add(hotelYes);
		hotelLine.add(hotelNo);
		
		
		/// Car line
		VerticalPanel carInfo =  new VerticalPanel();
		
		HorizontalPanel carLine =  new HorizontalPanel();
		Label carQuestion = new Label();
		carQuestion.setText("Do you have a car for the celebration ?");
		RadioButton carYes = new RadioButton("Car", "Yes");
		RadioButton carNo = new RadioButton("Car", "No");
		carNo.setValue(true);
		carLine.add(carQuestion);
		carLine.add(carYes);
		carLine.add(carNo);
				
		HorizontalPanel carDetails =  new HorizontalPanel();
		Label carDetailsQuestion = new Label();
		carDetailsQuestion.setText("If yes, how many free places do you have ?");
		TextBox carFreePlaceNumber = new TextBox();
		carDetails.add(carDetailsQuestion);
		carDetails.add(carFreePlaceNumber);
	
		carInfo.add(carLine);
		carInfo.add(carDetails);		
		
		
		///assembly logisticPanel
		logisticPanel.add(logisticLabel);
		logisticPanel.add(hotelLine);
		logisticPanel.add(carInfo);		
	}

}
