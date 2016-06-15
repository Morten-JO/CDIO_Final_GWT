package cdio.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Header extends Composite {
	private Label header;
	private Label name;
	private Label rolle;

	public Header(String navn) {
		VerticalPanel vPanel = new VerticalPanel();
		HorizontalPanel hPanel = new HorizontalPanel();
		initWidget(vPanel);
		header = new Label("Welcome");
		this.name = new Label("Logget ind som: " + navn);
		this.rolle = new Label("");
		header.setStyleName("Header-Text");
		this.name.setStyleName("Header-Text-rolle");
		this.rolle.setStyleName("Header-Text-rolle");
		vPanel.add(header);
		hPanel.add(this.name);
		hPanel.add(this.rolle);
		vPanel.add(hPanel);
		vPanel.setStyleName("Header");

	}

	public void setText(String string) {

		this.header.setText(string);
	}
	
	public void setRolleText(String rolleText){
		rolle.setText(", med rollen: "+rolleText);
	}
}
