package cdio.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;


public class Content extends Composite  {
	private HorizontalPanel hPanel;
	
	public Content(){
		hPanel = new HorizontalPanel();
		initWidget(hPanel);
		hPanel.setStyleName("Content");
	}
	
	public void clearPanel(){
		this.hPanel.clear();
		
	}
	
	public void addContent(Composite comp){
		this.hPanel.clear();
		this.hPanel.add(comp);
		
	}
}
