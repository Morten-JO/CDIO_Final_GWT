package cdio.client;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Header extends Composite {
	private Label header;
	private Label rolle;
	public Header(String navn){
		VerticalPanel vPanel = new VerticalPanel();
		initWidget(vPanel);
		header = new Label("Welcome");
		this.rolle = new Label("Logget ind som: "+navn);
		header.setStyleName("Header-Text");
		this.rolle.setStyleName("Header-Text-rolle");
		vPanel.add(header);
		vPanel.add(this.rolle);
		vPanel.setStyleName("Header");

		
		
	}

	

	public void setText(String string) {
		
		this.header.setText(string);
	}
}
