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
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio.client.service.ServiceClientImpl;
import cdio.shared.FieldVerifier;
import cdio.shared.UserDTO;

public class OpretBruger extends Composite {
	VerticalPanel addPanel;

	String[] content = { "ID", "Navn", "Initial", "CPR", "Password", "Rolle" };
	String[] rolle = { "Admin", "Farmaceut", "Værkfører", "Operatør" }; // dette
																		// bruges
																		// til
																		// visning
																		// på
																		// hjemmesiden
	String[] roller = { "admin", "farmaceut", "vaerkfoerer", "operatoer" }; // dette
																			// bruges
																			// til
																			// sql
																			// statement

	HorizontalPanel[] hPanel = new HorizontalPanel[content.length];
	Label[] labels = new Label[content.length];
	Label[] fejlLabels = new Label[content.length];
	TextBox[] txtbox = new TextBox[content.length - 1]; // dette liste er én
														// mindre end resten
	RadioButton[] radioButton = new RadioButton[roller.length]; // på grund af
																// det her :D

	Button save;

	boolean nameValid = false;
	boolean oprIDVaild = false;
	boolean iniVaild = false;
	boolean cprVaild = false;
	boolean passwordVaild = false;
	boolean radioButtonPressed = false;
	private final ServiceClientImpl client;
	private final String token;

	public OpretBruger(ServiceClientImpl client, String token) {
		addPanel = new VerticalPanel();
		this.client = client;
		this.token = token;
		addPanel.setHeight("120px");
		initWidget(this.addPanel);

		// opretter vandret paneler og tilføjer indhold i dem
		for (int i = 0; i < content.length; i++) {
			hPanel[i] = new HorizontalPanel();
			hPanel[i].setStyleName("StyleLabel");

			labels[i] = new Label(content[i]);
			labels[i].setWidth("80px");
			hPanel[i].add(labels[i]);

			if (i < content.length - 1) {
				txtbox[i] = new TextBox();
				txtbox[i].setPixelSize(176, 20);
				txtbox[i].setStyleName("TextBox-style1");
				hPanel[i].add(txtbox[i]);
			}

			fejlLabels[i] = new Label("");
			fejlLabels[i].setStyleName("labelAddView");
			hPanel[i].add(fejlLabels[i]);

			if (i == (content.length - 1)) {
				for (int j = 0; j < roller.length; j++) {
					radioButton[j] = new RadioButton("rolle", rolle[j]);
					hPanel[i].add(radioButton[j]);
				}
			}
		}

		save = new Button("Gem");
		save.setEnabled(false);

		save.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				String rolle = null;
				for (int i = 0; i < roller.length; i++) {
					if (radioButton[i].getValue())
						rolle = roller[i];
				}
				if(Window.confirm("Du er i gang med at lave en User med Id : "+txtbox[0].getText()+", Navn : "+txtbox[1].getText()+
						", Initial : "+txtbox[2].getText()+", CPR : "+txtbox[3].getText()+
						", Password : "+txtbox[4].getText()+", Rolle : "+rolle)){


				UserDTO newUser = new UserDTO(Integer.parseInt(txtbox[0].getText()), txtbox[1].getText(),
						txtbox[2].getText(), txtbox[3].getText(), txtbox[4].getText(), rolle);

				OpretBruger.this.client.service.createUser(OpretBruger.this.token, newUser, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Server fejl!" + caught.getMessage());

					}

					@Override
					public void onSuccess(Void result) {
						Window.alert("Operatør er nu oprettet!");

						for (TextBox box : txtbox) {
							box.setText("");
						}
						for (RadioButton rb : radioButton) {
							rb.setValue(false);
						}
						checkFormValid();
						save.setEnabled(false);
					}

				});
			}
			}
		});

		txtbox[0].addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				OpretBruger.this.client.service.getPersons(OpretBruger.this.token, new AsyncCallback<List<UserDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<UserDTO> result) {
						boolean idExists = false;
						try {
							Integer.parseInt(txtbox[0].getText());
							for (int i = 0; i < result.size(); i++) {
								if (result.get(i).getOprId() == Integer.parseInt(txtbox[0].getText())) {
									idExists = true;
								}
							}
							if (!idExists) {
								txtbox[0].setStyleName("TextBox-style");
								oprIDVaild = true;
								fejlLabels[0].setText("");
							} else {
								txtbox[0].setStyleName("gwt-TextBox-invalidEntry ");
								oprIDVaild = false;
								fejlLabels[0].setText("ID er optaget");
							}
						} catch (NumberFormatException e) {
							txtbox[0].setStyleName("gwt-TextBox-invalidEntry ");
							oprIDVaild = false;
							fejlLabels[0].setText("feltet må kun indholde tal!");
						}

					}

				});
				checkFormValid();
			}

		});

		// register event handlers
		txtbox[1].addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				OpretBruger.this.client.service.getPersons(OpretBruger.this.token, new AsyncCallback<List<UserDTO>>() {
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<UserDTO> result) {
						if (!FieldVerifier.isValidName(txtbox[1].getText())) {
							txtbox[1].setStyleName("gwt-TextBox-invalidEntry");
							nameValid = false;
							fejlLabels[1].setText("feltet må indholde op til 20 bogstaver");
						} else {
							txtbox[1].setStyleName("TextBox-style");
							nameValid = true;
							fejlLabels[1].setText("");
						}
					}
				});

				checkFormValid();
			}

		});

		txtbox[2].addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (!FieldVerifier.isValidIni(txtbox[2].getText())) {
					txtbox[2].setStyleName("gwt-TextBox-invalidEntry");
					iniVaild = false;
					fejlLabels[2].setText("Initialer skal være mellem 2 og 4 bogstaver!");
				} else {
					txtbox[2].setStyleName("TextBox-style");
					iniVaild = true;
					fejlLabels[2].setText("");
				}
				checkFormValid();
			}

		});

		txtbox[3].addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (!FieldVerifier.isValidCpr(txtbox[3].getText())) {
					txtbox[3].setStyleName("gwt-TextBox-invalidEntry");
					cprVaild = false;
					fejlLabels[3].setText("CPR skal være i formen ddmmyyxxxx");
				} else {
					txtbox[3].setStyleName("TextBox-style");
					cprVaild = true;
					fejlLabels[3].setText("");
				}
				checkFormValid();
			}

		});

		txtbox[4].addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {

				if (!FieldVerifier.isVaildPassword(txtbox[4].getText())) {
					txtbox[4].setStyleName("gwt-TextBox-invalidEntry");
					passwordVaild = false;
					fejlLabels[4].setText("password skal være mellem 5 og 8 tegn!");
				} else {
					txtbox[4].setStyleName("TextBox-style");
					passwordVaild = true;
					fejlLabels[4].setText("");
				}
				checkFormValid();
			}

		});

		for (RadioButton rb : radioButton) {
			rb.addClickHandler(new RBClickHandler());
		}

		for (int i = 0; i < content.length; i++) {
			addPanel.add(hPanel[i]);
		}
		addPanel.add(save);
		// addPanel.setCellHorizontalAlignment(save,
		// HasHorizontalAlignment.ALIGN_CENTER);

	}

	private void checkFormValid() {
		if (nameValid && oprIDVaild && iniVaild && cprVaild && passwordVaild && radioButtonPressed)
			save.setEnabled(true);
		else
			save.setEnabled(false);

	}

	private class RBClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (radioButton[0].getValue() || radioButton[1].getValue() || radioButton[2].getValue()
					|| radioButton[3].getValue()) {
				radioButtonPressed = true;
				checkFormValid();
			}
		}
	}

}
