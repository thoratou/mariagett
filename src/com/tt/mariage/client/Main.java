package com.tt.mariage.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tt.mariage.client.data.PersonTableHandler;
import com.tt.mariage.client.information.Information;
import com.tt.mariage.client.login.Login;
import com.tt.mariage.client.logistic.Logistic;
import com.tt.mariage.client.meal.Meal;


public class Main implements EntryPoint {

	public void onModuleLoad() {
		
		Window.setMargin("0px");
		
		//Person table handler
		PersonTableHandler personTableHandler = new PersonTableHandler();
		
		Login login = new Login(personTableHandler);
		login.login();
		
		VerticalPanel generalPanel = new VerticalPanel();
		generalPanel.setWidth("100%");
		
		//General info part
		VerticalPanel infoPanel = new VerticalPanel();
		Information information = new Information(personTableHandler);
		information.setPanel(infoPanel);
		
		//Meal part
		VerticalPanel mealPanel = new VerticalPanel();
		Meal meal = new Meal(personTableHandler);
		meal.setPanel(mealPanel);		

		/// Logistic part 
		VerticalPanel logisticPanel = new VerticalPanel();
		Logistic logistic = new Logistic();
		logistic.setPanel(logisticPanel);
		
		
		///Assembly
		generalPanel.add(infoPanel);
		generalPanel.add(new HTML("<br/><br/>"));
		generalPanel.add(mealPanel);
		generalPanel.add(new HTML("<br/><br/>"));
		generalPanel.add(logisticPanel);
		
		
		RootPanel.get().add(generalPanel);
	}

}
