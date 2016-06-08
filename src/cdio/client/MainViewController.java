package cdio.client;
import cdio.client.service.*;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainViewController extends Composite {
	
	private Header header;
	private Footer footer;
	private Menu menuView;
	private Content content;
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private ScrollPanel scroll;
	private ServiceClientImpl service;
	
	public Content getContent() {
		return content;
	}

	public ServiceClientImpl getService() {
		return service;
	}

	public MainViewController(ServiceClientImpl service){
		this.service = service;
		vPanel = new VerticalPanel();
		
		initWidget(vPanel);
		hPanel = new HorizontalPanel();
		header = new Header();
		
		
		footer = new Footer();
		
		menuView = new Menu(this);
	
		content = new Content(this.service);
		scroll = new ScrollPanel();
		scroll.add(content);
		scroll.setSize("864px", "400px");
		//scroll.setStyleName("ScrollPanel");
		
		vPanel.add(header);
		hPanel.add(menuView);
		hPanel.add(scroll);
		vPanel.add(hPanel);
		vPanel.add(footer);
		
		
}

	public void showPersons() {
		
		this.header.setText("Personer i systemet");
		this.content.addContent(new ShowPersons(this.service));
		
		
	}
	
	public void showTest() {
		
		this.header.setText("Test");
		this.content.addContent(new Raavare(this.service));
		
		
	}
}