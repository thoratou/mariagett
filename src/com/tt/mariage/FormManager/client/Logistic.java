package com.tt.mariage.FormManager.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;

public class Logistic extends Composite {

	public Logistic() {
		
		AbsolutePanel absolutePanel = new AbsolutePanel();
		initWidget(absolutePanel);
		absolutePanel.setSize("567px", "440px");
		
		Label lblLogisticPart = new Label("Logistic Part");
		absolutePanel.add(lblLogisticPart, 0, 10);
		lblLogisticPart.setSize("183px", "16px");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		absolutePanel.add(verticalPanel, 0, 27);
		verticalPanel.setSize("567px", "345px");
		
		DecoratorPanel decoratorPanel = new DecoratorPanel();
		verticalPanel.add(decoratorPanel);
		
		Grid grid = new Grid(5, 3);
		decoratorPanel.setWidget(grid);
		grid.setSize("436px", "139px");
		
		Label lblAuriezvousBesoinDun = new Label("Auriez-vous besoin d'un logement ?");
		grid.setWidget(0, 0, lblAuriezvousBesoinDun);
		
		RadioButton rdbtnOuiHotel = new RadioButton("new name", "Oui");
		grid.setWidget(0, 1, rdbtnOuiHotel);
		
		RadioButton rdbtnNonHotel = new RadioButton("new name", "Non");
		grid.setWidget(0, 2, rdbtnNonHotel);
		
		Label lblAuriezvousUnVhicule = new Label("Auriez-vous un v\u00E9hicule ?");
		grid.setWidget(1, 0, lblAuriezvousUnVhicule);
		
		RadioButton rdbtnOuiVoiture = new RadioButton("new name", "Oui");
		rdbtnOuiVoiture.setHTML("Oui");
		grid.setWidget(1, 1, rdbtnOuiVoiture);
		
		RadioButton rdbtnNonVoiture = new RadioButton("new name", "Non");
		rdbtnNonVoiture.setHTML("Non");
		grid.setWidget(1, 2, rdbtnNonVoiture);
		
		Label lblAutresRenseignements = new Label("Autres renseignements");
		grid.setWidget(2, 0, lblAutresRenseignements);
		
		RichTextArea richTextArea = new RichTextArea();
		grid.setWidget(3, 0, richTextArea);
		
		Button btnNewButton = new Button("Save");
		grid.setWidget(4, 0, btnNewButton);
	}
}
