package cdio.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Footer extends Composite{
	
	
	public Footer() {
		
		VerticalPanel footerPanel = new VerticalPanel();
		initWidget(footerPanel);
		Label footerLbl = new Label("© Copyright Gruppe 24");
		footerPanel.add(footerLbl);
		footerLbl.setStyleName("Footer-Text");
		footerPanel.setStyleName("Footer");
		
	}

}
