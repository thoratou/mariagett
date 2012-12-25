package com.tt.mariage.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tt.mariage.client.data.PersonTableHandler;
import com.tt.mariage.client.data.UserDataHandler;
import com.tt.mariage.client.information.Information;
import com.tt.mariage.client.login.Login;
import com.tt.mariage.client.logistic.Logistic;
import com.tt.mariage.client.meal.Meal;
import com.tt.mariage.client.services.UserData;


public class Main implements EntryPoint {
	
	public interface ImageResources extends ClientBundle {	 
	    public static final ImageResources INSTANCE = GWT.create(ImageResources.class);
	 
	    @Source("ban.png")
	    ImageResource ban();	 
	}

	public void onModuleLoad() {
		
		Window.setMargin("0px");
		
		//handlers
		UserDataHandler userDataHandler = new UserDataHandler(new UserData());
		
		PersonTableHandler personTableHandler = new PersonTableHandler();
		
		Login login = new Login(userDataHandler, personTableHandler);
		login.login();
		
		VerticalPanel generalPanel = new VerticalPanel();
		generalPanel.setWidth("100%");
		
		//Ban
		Image banImage = new Image(ImageResources.INSTANCE.ban());
		generalPanel.add(banImage);
		
		//General info part
		VerticalPanel infoPanel = new VerticalPanel();
		Information information = new Information(userDataHandler, personTableHandler);
		information.setPanel(infoPanel);
		
		//Meal part
		VerticalPanel mealPanel = new VerticalPanel();
		Meal meal = new Meal(userDataHandler, personTableHandler);
		meal.setPanel(mealPanel);		

		/// Logistic part 
		VerticalPanel logisticPanel = new VerticalPanel();
		Logistic logistic = new Logistic(userDataHandler);
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
