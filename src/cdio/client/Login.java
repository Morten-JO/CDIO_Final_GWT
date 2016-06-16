package cdio.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio.client.service.ServiceClientImpl;

public class Login extends Composite {
	private VerticalPanel vPanel = new VerticalPanel();
	private TextBox username;
	private PasswordTextBox pass;
	private ServiceClientImpl client;
	private Label statusLogin;
	private Button login;

	public Login(ServiceClientImpl service) {
		
		client = service;
		initWidget(this.vPanel);
		
		// Opretter de forskellige moduler
		Label myLbl = new Label(" Login");
		HorizontalPanel hPanel = new HorizontalPanel();
		Image user_icon = new Image("/images/icon-profile.png");
		this.username = new TextBox();
		Image lock_icon = new Image("/images/icon lock.png");
		this.pass = new PasswordTextBox();
		HorizontalPanel passPanel = new HorizontalPanel();
		this.login = new Button("Log ind");
		statusLogin = new Label(" ");
		
		// Giver dem CSS-styles 
		vPanel.setStyleName("LoginBox");
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		myLbl.setStyleName("LoginFont");
		username.getElement().setPropertyString("placeholder", "ENTER YOUR ID");
		username.setStyleName("LoginTBox");
		pass.getElement().setPropertyString("placeholder", "ENTER YOUR PASSWORD");
		pass.setStyleName("LoginTBox");
		username.setText("4");
		pass.setText("admin");
		login.setPixelSize(100, 30);
		login.setStyleName("loginbtn");
		
		
		
		//add de forksellige elementer til deres panels 
		vPanel.add(myLbl);
		hPanel.add(user_icon);
		hPanel.add(username);
		passPanel.add(lock_icon);
		passPanel.add(pass);
		vPanel.add(hPanel);
		vPanel.add(passPanel);
		vPanel.add(statusLogin);
		vPanel.add(login);
		
		
		// Metode til at klikke enter, for at logge ind
		pass.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					login.click();
				}

			}

		});

		// Når der klikkes på login kanppen, sender den et RPC kald til serveren 
		//og tjekker om password og username passer		
		login.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//tjekker om usernamer og password er gyldigt
				client.service.checkLogin(Integer.parseInt(username.getText()), pass.getText(),
						new AsyncCallback<String>() {
						// metode hvis kaldet gå godt
							@Override
							public void onSuccess(String result) {
								if (result.equals("Login not authorized!"))
									statusLogin.setText(result);
								else {
									RootPanel.get().clear();
									RootPanel.get().add(new MainViewController(client, result));
								}

							}
							// metode hvis kaldet gå dårligt 
							@Override
							public void onFailure(Throwable caught) {
								statusLogin.setText("connection failure");

							}
						});

				;

			}
		});

	}

}
