package cdio.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
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
		
		/*
		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		initWidget(this.vPanel);
		header = new Header();		
		//content = new VerticalPanel();
		footer = new Footer();
		menuView = new VerticalPanel();
		//menuView.setStyleName("Menubla");
		//content.setStyleName("Contentbla");
		vPanel.add(header);
		vPanel.add(menuView);
		//hPanel.add(content);
		vPanel.add(hPanel);
		vPanel.add(footer);
		RootPanel.get("h1").add(header);
		vPanel.setStyleName("body-style");
		hPanel.setStyleName("body-style");
		
		*/
		
		VerticalPanel vPanel = new VerticalPanel();
		Label h = new Label("Fisse hele natten");
		initWidget(vPanel);
		vPanel.setStyleName("body-style");
		vPanel.setBorderWidth(1);
		Header header = new Header();
		header.setStyleName("headerStyle");
		vPanel.add(header);
		hPanel = new HorizontalPanel();
		hPanel.setStyleName("body-style1");
		hPanel.setBorderWidth(1);
		Menu m = new Menu();
		m.setStyleName("navn");
		hPanel.add(m);
		Content c = new Content();
		c.setStyleName("content-style");
		hPanel.add(c);
		vPanel.add(hPanel);
		Footer f = new Footer();
		vPanel.add(f);
		
	}

}
