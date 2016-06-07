package cdio.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
		//header.setStyleName("Header");
		
		footer = new Footer();
		//footer.setStyleName("Footer");
		menuView = new Menu();
		//menuView.setStyleName("Menu");
		content = new Content();
		//content.setStyleName("Content");
		
		vPanel.add(header);
		hPanel.add(menuView);
		hPanel.add(content);
		vPanel.add(hPanel);
		vPanel.add(footer);
		
		
}
}