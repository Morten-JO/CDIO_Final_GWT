package cdio.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


import cdio.client.service.ServiceClientImpl;
import cdio.shared.FieldVerifier;
import cdio.shared.ProduktBatchDTO;
import cdio.shared.RaavareBatchDTO;
import cdio.shared.RaavareDTO;
import cdio.shared.ReceptDTO;

public class Raavare extends Composite{


	private FlexTable flex;
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private String token;
	private ServiceClientImpl client;

	TextBox raavIdTxt;
	TextBox raavNavnTxt;
	TextBox leverandoerTxt;

	TextBox addRaavareId;
	TextBox addRaavNavnTxt;
	TextBox	addLeverandoerTxt; 
	
	Button create ;
	
	boolean raavIdValid = false;
	boolean raavNavnValid = false;
	boolean leverandoerValid = false;
	
	boolean addRaavareIdValid = false;
	boolean addRaavNavnTxtValid = false;
	boolean addLeverandoerValid = false;

	int eventRowIndex;
	Anchor ok;
	Anchor previousCancel = null;

	public Raavare(ServiceClientImpl client, String token) {
		flex = new FlexTable();
		flex.setStyleName("FlexTable");
		flex.getRowFormatter().addStyleName(0,"FlexTable-Header");
		vPanel = new VerticalPanel();
		initWidget(vPanel);
		hPanel = new HorizontalPanel();
		Label oprtRec = new Label("Opret Ravarer: ");
		oprtRec.setStyleName("Font-RB");
		addRaavareId = new TextBox();
		addRaavareId.setStyleName("TextBox-style1");
		addRaavNavnTxt = new TextBox();
		addRaavNavnTxt.setStyleName("TextBox-style1");
		addLeverandoerTxt = new TextBox();
		//addLeverandoerTxt .setHeight("20px");
		addLeverandoerTxt .setStyleName("TextBox-style1");
		
		Label RaavareId = new Label("RaavareID : ");
		Label RaavareNavn = new Label("RaavareNavn : ");
		Label Leverandoer = new Label("Leverandoer : ");
		create = new Button("Create");
		//create.setStyleName("createbtn");
		create.setEnabled(false);
		vPanel.add(oprtRec);
		hPanel.add(RaavareId);
		hPanel.add(addRaavareId);
		hPanel.add(RaavareNavn);
		hPanel.add(addRaavNavnTxt);
		hPanel.add(Leverandoer);
		hPanel.add(addLeverandoerTxt);

		hPanel.add(create);
		vPanel.add(hPanel);
		
		this.token = token;
		this.client = client;
		
		addRaavareId.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				
				Raavare.this.client.service.getRaavare(Raavare.this.token, new AsyncCallback<List<RaavareDTO>>(){
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<RaavareDTO> result) {
						boolean idExists = false;
						try{
							Integer.parseInt(addRaavareId.getText());
							for(int i = 0; i < result.size(); i++){
								if(result.get(i).getRaavareId() == Integer.parseInt(addRaavareId.getText())){
									idExists = true;
									
								}
							}
							if(!idExists){
								addRaavareId.removeStyleName("gwt-TextBox-invalidEntry");
								addRaavareIdValid = true;
								//failOprIDLbl.setText("");
							}
							else{
								addRaavareId.setStyleName("gwt-TextBox-invalidEntry");
								addRaavareIdValid = false;
								//failOprIDLbl.setText("Optaget id!");
							}
						} catch(NumberFormatException e){
							addRaavareId.setStyleName("gwt-TextBox-invalidEntry");
							addRaavareIdValid = false;
							//failOprIDLbl.setText("Optaget id!");
						}
						
					}
					
				});
				if (!FieldVerifier.isValidRbId(addRaavareId.getText())) {
					addRaavareId.setStyleName("gwt-TextBox-invalidEntry");
					addRaavareIdValid = false;
				} else {
					addRaavareId.removeStyleName("gwt-TextBox-invalidEntry");
					addRaavareIdValid = true;
				}
				checkFormValid_Create();
			}

		});

		
		addRaavNavnTxt.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(!FieldVerifier.isValidName(addRaavNavnTxt.getText())){	
					addRaavNavnTxt.setStyleName("gwt-TextBox-invalidEntry");
				
				addRaavNavnTxtValid = false;
			} else {
				addRaavNavnTxt.removeStyleName("gwt-TextBox-invalidEntry");
				addRaavNavnTxtValid = true;

				checkFormValid_Create();
				
				
			}
			}
			});
		
		addLeverandoerTxt.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if(!FieldVerifier.isValidName(addLeverandoerTxt.getText())){	
					addLeverandoerTxt.setStyleName("gwt-TextBox-invalidEntry");
				
					addLeverandoerValid = false;
			} else {
				addLeverandoerTxt.removeStyleName("gwt-TextBox-invalidEntry");
				addLeverandoerValid = true;

				checkFormValid_Create();
				
				
			}
			}
			});
		
		create.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				int raId = Integer.parseInt(addRaavareId.getText());
				String raNavn = addRaavNavnTxt.getText();
				String lever = addLeverandoerTxt.getText();	
				
				RaavareDTO RA = new RaavareDTO(raId, raNavn, lever);
				Raavare.this.client.service.createRA(Raavare.this.token, RA, new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Server fejl!" + caught.getMessage());
						
					}

					@Override
					public void onSuccess(Void result) {
						addRaavareId.setText("");
						addRaavNavnTxt.setText("");
						addLeverandoerTxt.setText("");
						Raavare.this.client.service.getRaavare(Raavare.this.token, new AsyncCallback<List<RaavareDTO>>() {

							@Override
							public void onFailure(Throwable caught) {

							}

							@Override
							public void onSuccess(List<RaavareDTO> result) {

								flex.setText(0, 0, "RaavareId");
								flex.setText(0, 1, "Raavare_Navn");
								flex.setText(0, 2, "Leverandoer");

								for (int rowIndex = 0; rowIndex < result.size(); rowIndex++) {

									flex.setText(rowIndex + 1, 0, "" + result.get(rowIndex).getRaavareId());
									flex.setText(rowIndex + 1, 1, "" + result.get(rowIndex).getRaavareNavn());
									flex.setText(rowIndex + 1, 2, "" + result.get(rowIndex).getLeverandoer());
									
									flex.getCellFormatter().addStyleName(rowIndex+1, 0, "FlexTable-Cell");
									flex.getCellFormatter().addStyleName(rowIndex+1, 1, "FlexTable-Cell");
									flex.getCellFormatter().addStyleName(rowIndex+1, 2, "FlexTable-Cell");
									
									
									Anchor edit = new Anchor("edit");
									flex.setWidget(rowIndex + 1, 3, edit);

									edit.addClickHandler(new EditHandler());
								}

								// flex.setStyleName("FlexTable");

							}

						});
						
					}
					
				
				
			});
			
			}
			});
		
		client.service.getRaavare(token, new AsyncCallback<List<RaavareDTO>>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(List<RaavareDTO> result) {

				flex.setText(0, 0, "RaavareId");
				flex.setText(0, 1, "Raavare_Navn");
				flex.setText(0, 2, "Leverandoer");

				for (int rowIndex = 0; rowIndex < result.size(); rowIndex++) {

					flex.setText(rowIndex + 1, 0, "" + result.get(rowIndex).getRaavareId());
					flex.setText(rowIndex + 1, 1, "" + result.get(rowIndex).getRaavareNavn());
					flex.setText(rowIndex + 1, 2, "" + result.get(rowIndex).getLeverandoer());
					
					flex.getCellFormatter().addStyleName(rowIndex+1, 0, "FlexTable-Cell");
					flex.getCellFormatter().addStyleName(rowIndex+1, 1, "FlexTable-Cell");
					flex.getCellFormatter().addStyleName(rowIndex+1, 2, "FlexTable-Cell");
					
					
					Anchor edit = new Anchor("edit");
					flex.setWidget(rowIndex + 1, 3, edit);

					edit.addClickHandler(new EditHandler());
				}

				// flex.setStyleName("FlexTable");

			}

		});
		vPanel.add(flex);
		// create textboxs
		raavIdTxt = new TextBox();
		raavIdTxt.setWidth("20px");
		raavNavnTxt = new TextBox();
		raavNavnTxt.setWidth("60px");
		leverandoerTxt = new TextBox();
		leverandoerTxt.setWidth("80px");

	}

	private class EditHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			if (previousCancel != null)
				previousCancel.fireEvent(new ClickEvent() {
				});

			eventRowIndex = flex.getCellForEvent(event).getRowIndex();

			raavIdTxt.setText(flex.getText(eventRowIndex, 0));
			raavNavnTxt.setText(flex.getText(eventRowIndex, 1));
			leverandoerTxt.setText(flex.getText(eventRowIndex, 2));

			// flex.setWidget(eventRowIndex, 0, raavIdTxt);
			flex.setWidget(eventRowIndex, 1, raavNavnTxt);
			flex.setWidget(eventRowIndex, 2, leverandoerTxt);

			raavIdTxt.setFocus(true);

			final Anchor edit = (Anchor) event.getSource();

			final String raavid = raavIdTxt.getText();
			final String raavNavn = raavNavnTxt.getText();
			final String leverandoer = leverandoerTxt.getText();

			ok = new Anchor("ok");
			ok.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					flex.setText(eventRowIndex, 0, raavIdTxt.getText());
					flex.setText(eventRowIndex, 1, raavNavnTxt.getText());
					flex.setText(eventRowIndex, 2, leverandoerTxt.getText());

					RaavareDTO Raavare = new RaavareDTO(Integer.parseInt(raavIdTxt.getText()),
							raavNavnTxt.getText(), leverandoerTxt.getText());

					client.service.updateRaavare(Raavare.this.token, Raavare, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							System.out.println("nusnus");

						}

						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub

						}

					});
					flex.setWidget(eventRowIndex, 3, edit);
					flex.clearCell(eventRowIndex, 4);

					previousCancel = null;

				}
			});

			Anchor cancel = new Anchor("cancel");
			previousCancel = cancel;
			cancel.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					raavIdTxt.setText(raavid);
					raavIdTxt.fireEvent(new KeyUpEvent() {
					});

					raavNavnTxt.setText(raavNavn);
					raavNavnTxt.fireEvent(new KeyUpEvent() {
					}); // validation

					leverandoerTxt.setText(leverandoer);
					leverandoerTxt.fireEvent(new KeyUpEvent() {
					}); // validation

					flex.setText(eventRowIndex, 0, raavid);
					flex.setText(eventRowIndex, 1, raavNavn);
					flex.setText(eventRowIndex, 2, leverandoer);
					// restore edit link
					flex.setWidget(eventRowIndex, 3, edit);
					flex.clearCell(eventRowIndex, 4);

					previousCancel = null;
				}

			});



			raavNavnTxt.addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (!FieldVerifier.isValidName(raavNavnTxt.getText())) {
						raavNavnTxt.setStyleName("gwt-TextBox-invalidEntry");
						raavNavnValid = false;
					} else {
						raavNavnTxt.removeStyleName("gwt-TextBox-invalidEntry");
						raavNavnValid = true;
					}
					checkFormValid();
					;
				}

			});

			leverandoerTxt.addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (!FieldVerifier.isValidName(leverandoerTxt.getText())) {
						leverandoerTxt.setStyleName("gwt-TextBox-invalidEntry");
						leverandoerValid = false;
					} else {
						leverandoerTxt.removeStyleName("gwt-TextBox-invalidEntry");
						leverandoerValid = true;
					}
					checkFormValid();
					;
				}

			});

			flex.setWidget(eventRowIndex, 3, ok);
			flex.setWidget(eventRowIndex, 4, cancel);
		}
	}

	private void checkFormValid() {
		if (leverandoerValid && raavNavnValid)

			flex.setWidget(eventRowIndex, 3, ok);

		else
			  
			flex.setText(eventRowIndex, 3, "ok");
		

	}
	
	private void checkFormValid_Create() {
		if (addRaavareIdValid && addRaavNavnTxtValid && addLeverandoerValid ){
			create.setEnabled(true);			
		}
		else create.setEnabled(false);
			
		}
}