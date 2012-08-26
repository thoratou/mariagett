package com.tt.mariage.LoginManager.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasVerticalAlignment;

public class Login extends Composite {
	private TextBox textBoxUsername;
	private TextBox textBoxPassword;

	public Login() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel.add(verticalPanel_1);
		verticalPanel_1.setSize("452px", "143px");
		
		Label lblNewLabel = new Label("Sign in to your account");
		lblNewLabel.setStyleName("gwt-Label-Login");
		verticalPanel_1.add(lblNewLabel);
		
		FlexTable flexTable = new FlexTable();
		verticalPanel_1.add(flexTable);
		flexTable.setSize("499px", "238px");
		
		Label lblUsername = new Label("UserName :");
		lblUsername.setStyleName("gwt-Label-Login");
		flexTable.setWidget(0, 0, lblUsername);
		
		textBoxUsername = new TextBox();
		flexTable.setWidget(0, 1, textBoxUsername);
		
		Label lblNewLabel_1 = new Label("Password");
		lblNewLabel_1.setStyleName("gwt-Label-Login");
		flexTable.setWidget(1, 0, lblNewLabel_1);
		
		textBoxPassword = new TextBox();
		flexTable.setWidget(1, 1, textBoxPassword);
		
		Button btnSignIn = new Button("Sign In");
		btnSignIn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (textBoxUsername.getText().length() == 0 || textBoxPassword.getText().length() == 0)
				{
					Window.alert(" User name or password is empty.");
				}
				
			}
		});
		flexTable.setWidget(2, 1, btnSignIn);
		
		Label lblNotRegisterYet = new Label("Not register yet .");
		flexTable.setWidget(3, 1, lblNotRegisterYet);
		
		Button btnRegister = new Button("Register");
		flexTable.setWidget(4, 1, btnRegister);
		flexTable.getCellFormatter().setVerticalAlignment(4, 1, HasVerticalAlignment.ALIGN_TOP);
		flexTable.getCellFormatter().setVerticalAlignment(3, 1, HasVerticalAlignment.ALIGN_BOTTOM);
	}

}
