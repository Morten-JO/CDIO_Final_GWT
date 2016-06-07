package cdio.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;


public class Content extends Composite  {
	HorizontalPanel hPanel;
	
	public Content(){
		hPanel = new HorizontalPanel();
		initWidget(hPanel);
		hPanel.setStyleName("content-style");
	}
}
