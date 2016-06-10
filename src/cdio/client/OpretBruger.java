package cdio.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio.client.service.ServiceClientImpl;
import cdio.shared.FieldVerifier;
import cdio.shared.UserDTO;



public class OpretBruger extends Composite {
	VerticalPanel addPanel;

	// controls
	Label nameLbl;
	Label oprIDLbl;
	Label iniLbl;
	Label cprLbl;
	Label passwordLbl;
	
	Label failNameLbl;
	Label failOprIDLbl;
	Label failIniLbl;
	Label failCprLbl;
	Label failPasswordLbl;
	Label failRolle;
	
	RadioButton adminRB;
	RadioButton OperatoerRB;
	RadioButton farmaceutRB;
	RadioButton vaerkfoererRB;
	TextBox nameTxt;
	TextBox oprIDTxt;
	TextBox iniTxt;
	TextBox cprTxt;
	PasswordTextBox passwordTxt;
	
	Button save ;

	boolean nameValid = false;
	boolean oprIDVaild = false;
	boolean iniVaild = false;
	boolean cprVaild = false;
	boolean passwordVaild = false;
	boolean RadioButton = false;
	private final ServiceClientImpl client;
	private final String token;
	
	public OpretBruger(ServiceClientImpl client,String  token) {
		addPanel = new VerticalPanel();
		this.client = client;
		this.token = token;
		addPanel.setHeight("120px");	
		initWidget(this.addPanel);

		HorizontalPanel namePanel = new HorizontalPanel();
		namePanel.setStyleName("styleHP");
		HorizontalPanel oprIDPanel = new HorizontalPanel();
		oprIDPanel.setStyleName("styleLabel");
		HorizontalPanel iniPanel = new HorizontalPanel();
		iniPanel.setStyleName("styleLabel");
		HorizontalPanel cprPanel = new HorizontalPanel();
		cprPanel.setStyleName("styleLabel");
		HorizontalPanel passwordPanel = new HorizontalPanel();
		passwordPanel.setStyleName("styleLabel");
		//FlowPanel RBpanel = new FlowPanel();
		HorizontalPanel RBpanel = new HorizontalPanel();
		RBpanel.setStyleName("styleLabel");

		nameLbl = new Label("Navn:");
		nameLbl.setWidth("80px");
		nameTxt = new TextBox();
		nameTxt.setHeight("1em");
		failNameLbl = new Label("");
		failNameLbl.setStyleName("labelAddView");
		namePanel.add(nameLbl);
		namePanel.add(nameTxt);
		namePanel.add(failNameLbl);


		oprIDLbl = new Label("OprID :");
		oprIDLbl.setWidth("80px");
		oprIDTxt = new TextBox();
		oprIDTxt.setHeight("1em");
		failOprIDLbl = new Label("");
		failOprIDLbl.setStyleName("labelAddView");
		oprIDPanel.add(oprIDLbl);
		oprIDPanel.add(oprIDTxt);
		oprIDPanel.add(failOprIDLbl);
		
		iniLbl = new Label("Ini:");
		iniLbl.setWidth("80px");
		iniTxt = new TextBox();
		iniTxt.setHeight("1em");
		failIniLbl = new Label("");
		failIniLbl.setStyleName("labelAddView");
		iniPanel.add(iniLbl);
		iniPanel.add(iniTxt);
		iniPanel.add(failIniLbl);
		
		cprLbl = new Label("CPR:");
		cprLbl.setWidth("80px");
		cprTxt = new TextBox();
		cprTxt.setHeight("1em");
		failCprLbl = new Label("");
		failCprLbl.setStyleName("labelAddView");
		cprPanel.add(cprLbl);
		cprPanel.add(cprTxt);
		cprPanel.add(failCprLbl);
		
		passwordLbl = new Label("Password:");
		passwordLbl.setWidth("80px");
		passwordTxt = new PasswordTextBox();
		passwordTxt.setPixelSize(176, 13);
		passwordTxt.setHeight("1em");
		failPasswordLbl = new Label("");
		failPasswordLbl.setStyleName("labelAddView1");
		passwordPanel.add(passwordLbl);
		passwordPanel.add(passwordTxt);
		passwordPanel.add(failPasswordLbl);
		
		adminRB = new RadioButton("rolle", "Admin");
		OperatoerRB = new RadioButton("rolle", "Operatoer");
		farmaceutRB = new RadioButton("rolle", "Farmaceut");
		vaerkfoererRB = new RadioButton("rolle","Vaerkfoerer");
		Label l = new Label ("Rolle :");
		l.setWidth("80px");
		l.setStyleName("styleTest");
		failRolle = new Label("");
		RBpanel.add(l);
		RBpanel.add(adminRB);
		RBpanel.add(OperatoerRB);
		RBpanel.add(farmaceutRB);
		RBpanel.add(vaerkfoererRB);
		RBpanel.add(failRolle);
		
	
		
		// use unicode escape sequence \u00F8 for 'ø'
		save = new Button("Tilf\u00F8j");
		save.setEnabled(false);
		
		save.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String rolle = null;
				if (adminRB.getValue()){
				rolle = "admin";
				}
				if(farmaceutRB.getValue()){
					rolle = "farmaceut";
				}
				if (vaerkfoererRB.getValue()){
					rolle = "vaerkfoerer";
				}
				if(OperatoerRB.getValue()){
					rolle = "operatoer";
				}
				UserDTO newUser = new UserDTO(Integer.parseInt(oprIDTxt.getText()),nameTxt.getText(),iniTxt.getText(),cprTxt.getText(),passwordTxt.getText(),rolle);
				
				OpretBruger.this.client.service.createUser(OpretBruger.this.token, newUser, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Server fejl!" + caught.getMessage());
						
					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("Personen er nu gemt");
						oprIDTxt.setText("");
						nameTxt.setText("");
						iniTxt.setText("");
						cprTxt.setText("");
						passwordTxt.setText("");
						vaerkfoererRB.setValue(false);
						adminRB.setValue(false);
						OperatoerRB.setValue(false);
						farmaceutRB.setValue(false);
						checkFormValid();
						save.setEnabled(false);
					}

				});
			}
		});
			
		
		

		// register event handlers
		nameTxt.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				OpretBruger.this.client.service.getPersons(OpretBruger.this.token, new AsyncCallback<List<UserDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<UserDTO> result) {
						boolean alreadyExists = false;
						for(int i = 0; i < result.size(); i++){
							if(result.get(i).getNavn().equals(nameTxt.getText())){
								alreadyExists = true;
								failNameLbl.setText("");
							}
						}
						if(!alreadyExists){
							if (!FieldVerifier.isValidName(nameTxt.getText())) {
								nameTxt.setStyleName("gwt-TextBox-invalidEntry");
								nameValid = false;
								failNameLbl.setText("D\u00E5rligt navn!");
							}
							else{
								nameTxt.removeStyleName("gwt-TextBox-invalidEntry");
								nameValid = true;
								failNameLbl.setText("");
							}
						}
						else{
							nameTxt.setStyleName("gwt-TextBox-invalidEntry");
							nameValid = false;
							failNameLbl.setText("D\u00E5rligt navn!");
						}
					}
				});
				
				checkFormValid();
			}

		});
					
					

		
		oprIDTxt.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				OpretBruger.this.client.service.getPersons(OpretBruger.this.token,new AsyncCallback<List<UserDTO>>(){
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<UserDTO> result) {
						boolean idExists = false;
						try{
							Integer.parseInt(oprIDTxt.getText());
							for(int i = 0; i < result.size(); i++){
								if(result.get(i).getOprId() == Integer.parseInt(oprIDTxt.getText())){
									idExists = true;
									
								}
							}
							if(!idExists){
								oprIDTxt.removeStyleName("gwt-TextBox-invalidEntry");
								oprIDVaild = true;
								failOprIDLbl.setText("");
							}
							else{
								oprIDTxt.setStyleName("gwt-TextBox-invalidEntry");
								oprIDVaild = false;
								failOprIDLbl.setText("Optaget id!");
							}
						} catch(NumberFormatException e){
							oprIDTxt.setStyleName("gwt-TextBox-invalidEntry");
							oprIDVaild = false;
							failOprIDLbl.setText("Optaget id!");
						}
						
					}
					
				});
				checkFormValid();
			}

		});
					
					

		
		iniTxt.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (!FieldVerifier.isValidIni(iniTxt.getText())) {
					iniTxt.setStyleName("gwt-TextBox-invalidEntry");
					iniVaild = false;
					failIniLbl.setText("D\u00E5rligt ini, max 2 bogstaver!");
				}
				else {
					iniTxt.removeStyleName("gwt-TextBox-invalidEntry");
					iniVaild = true;
					failIniLbl.setText("");
				}
				checkFormValid();
			}

		});
		
		cprTxt.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (!FieldVerifier.isValidCpr(cprTxt.getText())) {
					cprTxt.setStyleName("gwt-TextBox-invalidEntry");
					cprVaild = false;
					failCprLbl.setText("D\u00E5rligt cpr skriv i formen xxxxxxxxxx!");
				}
				else {
					cprTxt.removeStyleName("gwt-TextBox-invalidEntry");
					cprVaild = true;
					failCprLbl.setText("");
				}
				checkFormValid();
			}

		});
		
		passwordTxt.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
		
				if (!FieldVerifier.isVaildPassword(passwordTxt.getText())) {
					passwordTxt.setStyleName("gwt-TextBox-invalidEntry");
					passwordVaild = false;
					failPasswordLbl.setText("D\u00E5rligt password!");
				}
				else {
					passwordTxt.removeStyleName("gwt-TextBox-invalidEntry");
					passwordVaild = true;
					failPasswordLbl.setText("");
				}
				checkFormValid();
			}

		});
		
		adminRB.addClickHandler(new RBClickHandler ());
		OperatoerRB.addClickHandler(new RBClickHandler ());
		farmaceutRB.addClickHandler(new RBClickHandler ());
		vaerkfoererRB.addClickHandler(new RBClickHandler ());
		addPanel.add(namePanel);
		addPanel.add(oprIDPanel);
		addPanel.add(iniPanel);
		addPanel.add(cprPanel);
		addPanel.add(passwordPanel);
		addPanel.add(RBpanel);
		addPanel.add(save);
		//addPanel.setCellHorizontalAlignment(save, HasHorizontalAlignment.ALIGN_CENTER);


	}

	private void checkFormValid() {
		if (nameValid && oprIDVaild && iniVaild && cprVaild && passwordVaild && RadioButton )
			save.setEnabled(true);
		else
			save.setEnabled(false);

	}
	
	private class RBClickHandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			// TODO Auto-generated method stub
			
			if( adminRB.getValue()|| OperatoerRB.getValue() || farmaceutRB.getValue()|| vaerkfoererRB.getValue()){
				RadioButton = true; 
				checkFormValid();
			}
		}
		
	}
		
	}
