package com.tt.mariage.client;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.tt.mariage.client.data.PersonTableHandler;

public class Meal {

	private final PersonTableHandler personTableHandler;

	public Meal(PersonTableHandler personTableHandler) {
		this.personTableHandler = personTableHandler;
	}

	public void setPanel(VerticalPanel mealPanel) {
		Label mealLabel = new Label();
		mealLabel.setStyleName("h1");
		mealLabel.setText("Meal");
		
		mealPanel.add(mealLabel);
	}

}
