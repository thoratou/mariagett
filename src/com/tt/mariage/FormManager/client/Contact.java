package com.tt.mariage.FormManager.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalSplitPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Button;

public class Contact extends Composite {

	public Contact() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		verticalPanel.setSize("743px", "606px");
		
		Label lblFicheInivite = new Label("Invite File");
		verticalPanel.add(lblFicheInivite);
		
		FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		flexTable.setSize("410px", "235px");
		
		Label lblFirstname = new Label("FirstName");
		flexTable.setWidget(0, 0, lblFirstname);
		
		TextBox textBoxFirstName = new TextBox();
		flexTable.setWidget(0, 1, textBoxFirstName);
		textBoxFirstName.setWidth("237px");
		
		Label lblLastName = new Label("LastName");
		flexTable.setWidget(1, 0, lblLastName);
		
		TextBox textBoxLastName = new TextBox();
		flexTable.setWidget(1, 1, textBoxLastName);
		textBoxLastName.setWidth("233px");
		
		Label lblTypeMeal = new Label("Type Meal");
		flexTable.setWidget(2, 0, lblTypeMeal);
		
		FlexTable flexTable_1 = new FlexTable();
		flexTable.setWidget(2, 1, flexTable_1);
		flexTable_1.setSize("239px", "40px");
		
		RadioButton rdbtnMeat = new RadioButton("new name", "Meat");
		flexTable_1.setWidget(0, 0, rdbtnMeat);
		
		RadioButton rdbtnFish = new RadioButton("new name", "Fish");
		flexTable_1.setWidget(0, 1, rdbtnFish);
		
		RadioButton rdbtnChild = new RadioButton("new name", "Child");
		flexTable_1.setWidget(0, 2, rdbtnChild);
		
		Button btnCreate = new Button("Save");
		flexTable.setWidget(3, 1, btnCreate);
	}

}
