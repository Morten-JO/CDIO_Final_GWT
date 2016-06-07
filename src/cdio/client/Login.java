package cdio.client;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Login extends Composite{
	private VerticalPanel vPanel = new VerticalPanel();
	private TextBox username;
	private PasswordTextBox pass;

	public Login(){
		initWidget(this.vPanel);
		vPanel.setStyleName("LoginBox");
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		Label myLbl = new Label(" Login");
		myLbl.setStyleName("LoginFont");
		vPanel.add(myLbl);
		HorizontalPanel hPanel = new HorizontalPanel();
		Image user_icon = new Image("/images/icon-profile.png");
		hPanel.add(user_icon);
		this.username = new TextBox();
		username.getElement().setPropertyString("placeholder", "ENTER YOUR ID");
		username.setStyleName("LoginTBox");
		hPanel.add(username);
		Image lock_icon = new Image("/images/icon lock.png");
		this.pass = new PasswordTextBox();
		HorizontalPanel passPanel = new HorizontalPanel();
		pass.getElement().setPropertyString("placeholder", "ENTER YOUR PASSWORD");
		pass.setStyleName("LoginTBox");
		passPanel.add(lock_icon);
		passPanel.add(pass);
		
		Button login = new Button("Log ind");
		login.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get().clear();
				RootPanel.get().add(new MainViewController());
				
			}
		});
			
		
		login.setPixelSize(100, 30);
		login.setStyleName("loginbtn");
		
		vPanel.add(hPanel);
		vPanel.add(passPanel);
		vPanel.add(login);
	}

}
