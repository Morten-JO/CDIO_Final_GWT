package cdio.client;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Header extends Composite {
	
	
	public Header(){
		VerticalPanel vPanel = new VerticalPanel();
		initWidget(vPanel);
		Label header = new Label("Headertext");
		header.setStyleName("Header-Text");
		vPanel.add(header);
		vPanel.setStyleName("Header");

		
		
	}
}
