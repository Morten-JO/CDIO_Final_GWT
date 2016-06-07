package cdio.client;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainViewController extends Composite {
	
	private Header header;
	private Footer footer;
	private Menu menuView;
	private Content content;
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	
	public MainViewController(){
		
		vPanel = new VerticalPanel();
		initWidget(vPanel);
		hPanel = new HorizontalPanel();
		header = new Header();
		
		
		footer = new Footer();
		
		menuView = new Menu(this);
	
		content = new Content();
		
		vPanel.add(header);
		hPanel.add(menuView);
		hPanel.add(content);
		vPanel.add(hPanel);
		vPanel.add(footer);
		
		
}

	public void showPersons() {
		
		this.content.addContent(new Login());
		this.header.setText("Login");
		
	}
}