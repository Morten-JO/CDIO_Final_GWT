package cdio.client;
import cdio.client.service.*;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
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
	private ServiceClientImpl client;
	public static String token;
	
	


	public MainViewController(ServiceClientImpl service, String token){
		
		MainViewController.token = token; 
		client = service;
		vPanel = new VerticalPanel();
		
		initWidget(vPanel);
		hPanel = new HorizontalPanel();
		header = new Header();
		
		
		footer = new Footer();

		content = new Content(client);
		scroll = new ScrollPanel();
		scroll.add(content);
		scroll.setSize("864px", "400px");
		
		client.service.getRole(token,new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String result) {
				
				menuView = new Menu(MainViewController.this, result);
				vPanel.add(header);
				hPanel.add(menuView);
				hPanel.add(scroll);
				vPanel.add(hPanel);
				vPanel.add(footer);
				
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		} );
		//menuView = new Menu(this, token);
	
		//scroll.setStyleName("ScrollPanel");
		
	
		
}

	public void showRaavBa() {
		
		this.header.setText("Raavarebatch");
		this.content.addContent(new Raavarebatch(client));
		
		
	}
	
public void showProBa() {
		
		this.header.setText("Produktbatch");
		this.content.addContent(new Produktbatch(client));
		
		
	}

public void showRecepter() {
	
	this.header.setText("Recepter");
	this.content.addContent(new Recept(client));
	
	
}

public void showRaavarer() {
	
	this.header.setText("Raavarer");
	this.content.addContent(new Raavare(client));
	
	
}

public void opretBruger() {
	
	this.header.setText("Opret Bruger");
	this.content.addContent(new OpretBruger(client));
	
	
}
	
	public void redigerBruger() {
		
		this.header.setText("Rediger Bruger");
		this.content.addContent(new ShowPersons(client));
		
		
	}
	
	public void logUd(){
		RootPanel.get().clear();
		RootPanel.get().add(new Login(client));
		
	}
	
	public Content getContent() {
		return content;
	}

	public ServiceClientImpl getService() {
		return client;
	}

}