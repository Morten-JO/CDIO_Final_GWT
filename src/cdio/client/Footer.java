package cdio.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class Footer extends Composite{
	
	private HorizontalPanel footerPanel;
	
	public Footer() {
		footerPanel = new HorizontalPanel();
		initWidget(this.footerPanel);
		
		footerPanel.setStyleName("footer");
		
	}

}
