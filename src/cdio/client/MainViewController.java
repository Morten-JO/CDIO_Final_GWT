package cdio.client;

import cdio.client.service.*;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
	private String nameOfUser;

	public MainViewController(ServiceClientImpl service, String token) {

		MainViewController.token = token;
		client = service;
		vPanel = new VerticalPanel();

		initWidget(vPanel);
		hPanel = new HorizontalPanel();

		client.service.getUserName(token, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				header = new Header("");

			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				nameOfUser = result;
				// Window.alert(result);
				// System.out.println(result+"morten");
				header = new Header(nameOfUser);

			}

		});

		footer = new Footer();

		content = new Content(client);
		scroll = new ScrollPanel();
		scroll.add(content);
		scroll.setSize("864px", "400px");

		client.service.getRole(token, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				header.setRolleText(result);
				menuView = new Menu(MainViewController.this, result);
				if (header != null) {
					vPanel.add(header);
				} else {
					vPanel.add(new Header("."));
				}
				hPanel.add(menuView);
				hPanel.add(scroll);
				vPanel.add(hPanel);
				vPanel.add(footer);

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});
		
	}

	public void showRaavBa() {

		this.header.setText("Råvarebatch");
		this.content.addContent(new Raavarebatch(client, token));

	}

	public void showPrintProduktBatch(int number) {
		this.header.setText("Produktbatch print");
		this.content.addContent(new PrintProduktBatch(client, number, token));
	}

	public void showProBa() {

		this.header.setText("Produktbatch");
		this.content.addContent(new Produktbatch(client, token, this));

	}

	public void showRecepter() {

		this.header.setText("Recepter");
		this.content.addContent(new Recept(client, token));

	}

	public void showRaavarer() {

		this.header.setText("Råvarer");
		this.content.addContent(new Raavare(client, token));

	}

	public void opretBruger() {

		this.header.setText("Opret Bruger");
		this.content.addContent(new OpretBruger(client, token));

	}

	public void visBruger() {

		this.header.setText("Brugerliste");
		this.content.addContent(new ShowPersons(client, token));

	}
	
	public void viewBruger(){
		this.header.setText("Brugerliste");
		this.content.addContent(new UserView(client, token));

	}

	public void logUd() {
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