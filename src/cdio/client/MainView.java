package cdio.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainView extends Composite {
	
	private Header header;
	private Footer footer;
	private VerticalPanel menuView;
	private VerticalPanel content;
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	
	public MainView(){
//		Label lbl = new Label("du er grim");

//		vPanel.setBorderWidth(1);
//		header = new Header();
//		header.setStyleName("headerStyle");
//		footer = new Footer();
//		vPanel.add(header);
//		vPanel.add(footer);
//		vPanel.add(lbl);
		
		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		initWidget(this.vPanel);
		header = new Header();		
		content = new VerticalPanel();
		footer = new Footer();
		menuView = new VerticalPanel();
		menuView.setStyleName("Menubla");
		content.setStyleName("Contentbla");
		vPanel.add(header);
		hPanel.add(menuView);
		hPanel.add(content);
		vPanel.add(hPanel);
		vPanel.add(footer);
		
		vPanel.setStyleName("body-style");
		hPanel.setStyleName("body-style");
		
		
		
	}

}
