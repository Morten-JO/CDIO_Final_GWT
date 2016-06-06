package cdio.client;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Header extends Composite {
	private VerticalPanel vPanel = new VerticalPanel();
	
	public Header(){
		initWidget(this.vPanel);
		Label header = new Label("Headertext");
		header.setStyleName("headerFont");
		vPanel.add(header);
		vPanel.setStyleName("headerStyle");

		
		
	}
}
