package com.tt.mariage.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
	
	private MainConstants mainConstants = GWT.create(MainConstants.class);
	
	public interface ImageResources extends ClientBundle {	 
	    public static final ImageResources INSTANCE = GWT.create(ImageResources.class);
	 
	    @Source("ban.png")
	    ImageResource ban();	 
	    
	    @Source("menu_fr.png")
	    ImageResource menuFr();	 	
	    
	    @Source("menu_en.png")
	    ImageResource menuEn();	 
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
		generalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		
		//Ban
		Image banImage = new Image(ImageResources.INSTANCE.ban());
		generalPanel.add(banImage);
		
		//General info
		
		
		//People + Meal + Info panel
		HorizontalPanel firstPanel = new HorizontalPanel();
		firstPanel.setWidth("1116px");
		firstPanel.setSpacing(30);
		VerticalPanel firstVertical = new VerticalPanel();
		firstVertical.setWidth("550px");
		
		//General info part
		VerticalPanel infoPanel = new VerticalPanel();
		infoPanel.setWidth("100%");
		Information information = new Information(userDataHandler, personTableHandler);
		information.setPanel(infoPanel);
		
		//Meal part
		VerticalPanel mealPanel = new VerticalPanel();
		mealPanel.setWidth("100%");
		Meal meal = new Meal(userDataHandler, personTableHandler);
		meal.setPanel(mealPanel);		
		
		/// Logistic part 
		VerticalPanel logisticPanel = new VerticalPanel();
		logisticPanel.setWidth("100%");
		Logistic logistic = new Logistic(userDataHandler);
		logistic.setPanel(logisticPanel);
		
		firstVertical.add(infoPanel);
		firstVertical.add(new HTML("<br/><br/>"));
		firstVertical.add(mealPanel);
		firstVertical.add(new HTML("<br/><br/>"));
		firstVertical.add(logisticPanel);
		
		firstPanel.add(firstVertical);
		
		//Menu + Hotel pricing
		VerticalPanel secondVertical = new VerticalPanel();
		
		// menu image
		String menuFile = mainConstants.menuFile();
		Image menuImage = null;
		if("menu_fr".equals(menuFile)){
			menuImage = new Image(ImageResources.INSTANCE.menuFr());
		}
		else if("menu_en".equals(menuFile)){
			menuImage = new Image(ImageResources.INSTANCE.menuEn());
		}
		
		//hotel pricing
		DecoratorPanel hotelPanel = new DecoratorPanel();
		hotelPanel.setWidth("100%");
		FlexTable hotelTable = new FlexTable();
		hotelTable.setCellSpacing(6);
		hotelTable.setWidget(0, 0, new HTML(mainConstants.general()));
		hotelPanel.setWidget(hotelTable);
		
		if(menuImage != null){
			secondVertical.setCellHorizontalAlignment(menuImage, HasHorizontalAlignment.ALIGN_RIGHT);
			secondVertical.setCellVerticalAlignment(menuImage, HasVerticalAlignment.ALIGN_TOP);
			secondVertical.add(menuImage);
		}
		secondVertical.add(new HTML("<br/><br/>"));
		secondVertical.add(hotelPanel);
		
		firstPanel.add(secondVertical);

		///Assembly
		generalPanel.add(firstPanel);
		
		RootPanel.get().add(generalPanel);
	}

}
