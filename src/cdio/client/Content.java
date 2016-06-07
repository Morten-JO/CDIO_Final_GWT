package cdio.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio.client.service.ServiceClientImpl;


public class Content extends Composite  {
	private HorizontalPanel vPanel;
	private Label lbl;
	
	public Content(ServiceClientImpl client){
		vPanel = new HorizontalPanel();
		initWidget(vPanel);
//		lbl = new Label("Jensen er lazy prowgrammer");
//		lbl.setStyleName("Header-Text");
		
		vPanel.setStyleName("Content");
		
	}
	
	public void clearPanel(){
		this.vPanel.clear();
		
	}
	
	public void addContent(Composite comp){
		this.vPanel.clear();
	
		this.vPanel.add(comp);
		
		
	}
}
