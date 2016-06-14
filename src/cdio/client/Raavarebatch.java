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
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import cdio.shared.FieldVerifier;

import cdio.client.service.ServiceClientImpl;
import cdio.shared.RaavareDTO;
import cdio.shared.UserDTO;
import cdio.shared.RaavareBatchDTO;

public class Raavarebatch extends Composite {

	private FlexTable flex;
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private String token;
	private ServiceClientImpl client;
	
	TextBox AddRbId;
	ListBox AddRaavareId;
	TextBox AddMaengde;
	
	Button create ;
	
	TextBox rbIdTxt;
	TextBox raavareIdTxt;
	TextBox maengdeTxt;
	
	boolean RbIdVaild = false; 
	//boolean raavareId = false;
	boolean maengde = false;

	boolean rbIdValid = true;
	boolean raavareIdValid = true;
	boolean maengdeValid = true;

	int eventRowIndex;
	Anchor ok;
	Anchor previousCancel = null;

	public Raavarebatch(ServiceClientImpl client, String token) {
		flex = new FlexTable();
		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		Label oprtRB = new Label("Opret Raavarebatch :");
		oprtRB.setStyleName("Font-RB");
		flex.setStyleName("FlexTable");
		flex.getRowFormatter().addStyleName(0,"FlexTable-Header");
		initWidget(vPanel);
		this.token = token;
		this.client = client;
		
		AddRbId = new TextBox();
		AddRbId.setStyleName("TextBox-style");
		AddRaavareId = new ListBox();
		AddRaavareId.setHeight("20px");
		AddRaavareId.setStyleName("TextBox-style");
		
		
		client.service.getRaavare(token,new AsyncCallback<List<RaavareDTO>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<RaavareDTO> result) {
				// TODO Auto-generated method stub
				for (int i = 0; i < result.size(); i++) {
					AddRaavareId.addItem(Integer.toString(result.get(i).getRaavareId()));
				}
			}

		});
		
		AddMaengde = new TextBox();
		AddMaengde.setStyleName("TextBox-style");
		Label RbId = new Label("Råvarebatch ID : ");
		Label Raavare = new Label("Råvare ID : ");
		Label Maengde = new Label("Mængde : ");
		create = new Button("OPRET");
		//create.setStyleName("createbtn");
		create.setEnabled(false);
		vPanel.add(oprtRB);
		hPanel.add(RbId);
		hPanel.add(AddRbId);
		hPanel.add(Raavare);
		hPanel.add(AddRaavareId);
		hPanel.add(Maengde);
		hPanel.add(AddMaengde);
		hPanel.add(create);
		vPanel.add(hPanel);
		
		AddRbId.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				Raavarebatch.this.client.service.getRaavareBatches(new AsyncCallback<List<RaavareBatchDTO>>(){
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<RaavareBatchDTO> result) {
						boolean idExists = false;
						try{
							Integer.parseInt(AddRbId.getText());
							for(int i = 0; i < result.size(); i++){
								if(result.get(i).getRbId() == Integer.parseInt(AddRbId.getText())){
									idExists = true;
									
								}
							}
							if(!idExists){
								AddRbId.removeStyleName("gwt-TextBox-invalidEntry");
								rbIdValid = true;
								//failOprIDLbl.setText("");
							}
							else{
								AddRbId.setStyleName("gwt-TextBox-invalidEntry");
								RbIdVaild = false;
								//failOprIDLbl.setText("Optaget id!");
							}
						} catch(NumberFormatException e){
							AddRbId.setStyleName("gwt-TextBox-invalidEntry");
							rbIdValid = false;
							//failOprIDLbl.setText("Optaget id!");
						}
						
					}
					
				});
				if (!FieldVerifier.isValidRbId(AddRbId.getText())) {
					AddRbId.setStyleName("gwt-TextBox-invalidEntry");
					RbIdVaild = false;
				} else {
					AddRbId.removeStyleName("gwt-TextBox-invalidEntry");
					RbIdVaild = true;
				}
				checkFormValid_Create();
			}

		});
				
		
		//raavareId_KeyHandler(AddRaavareId);
		AddMaengde.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (!FieldVerifier.isValidMaengde(AddMaengde.getText())) {
					AddMaengde.setStyleName("gwt-TextBox-invalidEntry");
					maengde = false;
				} else {
					AddMaengde.removeStyleName("gwt-TextBox-invalidEntry");
					maengde = true;
				}
				checkFormValid_Create();
				
				
			}

		});
		
		create.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int rbId = Integer.parseInt(AddRbId.getText());
				int raavareId = Integer.parseInt(AddRaavareId.getSelectedItemText());
				double maengde = Double.parseDouble(AddMaengde.getText());
				
				
				RaavareBatchDTO RB = new RaavareBatchDTO(rbId, raavareId, maengde);
				Raavarebatch.this.client.service.createRB(Raavarebatch.this.token, RB, new AsyncCallback<Void>(){
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Server fejl!" + caught.getMessage());
							
						}

						@Override
						public void onSuccess(Void result) {
							Raavarebatch.this.client.service.getRaavareBatches(new AsyncCallback<List<RaavareBatchDTO>>() {

								@Override
								public void onFailure(Throwable caught) {

								}

								@Override
								public void onSuccess(List<RaavareBatchDTO> result) {

									flex.setText(0, 0, "rbId");
									flex.setText(0, 1, "raavareId");
									flex.setText(0, 2, "maengde");

									for (int rowIndex = 0; rowIndex < result.size(); rowIndex++) {

										flex.setText(rowIndex + 1, 0, "" + result.get(rowIndex).getRbId());
										flex.setText(rowIndex + 1, 1, "" + result.get(rowIndex).getRaavareId());
										flex.setText(rowIndex + 1, 2, "" + result.get(rowIndex).getMaengde());
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
							checkFormValid();
							create.setEnabled(false);
							
						}

					});
				}
			});
		
		
		
		
		client.service.getRaavareBatches(new AsyncCallback<List<RaavareBatchDTO>>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(List<RaavareBatchDTO> result) {

				flex.setText(0, 0, "rbId");
				flex.setText(0, 1, "raavareId");
				flex.setText(0, 2, "maengde");

				for (int rowIndex = 0; rowIndex < result.size(); rowIndex++) {

					flex.setText(rowIndex + 1, 0, "" + result.get(rowIndex).getRbId());
					flex.setText(rowIndex + 1, 1, "" + result.get(rowIndex).getRaavareId());
					flex.setText(rowIndex + 1, 2, "" + result.get(rowIndex).getMaengde());
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
		rbIdTxt = new TextBox();
		rbIdTxt.setWidth("20px");
		raavareIdTxt = new TextBox();
		raavareIdTxt.setWidth("60px");
		maengdeTxt = new TextBox();
		maengdeTxt.setWidth("80px");

	}

	private class EditHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			if (previousCancel != null)
				previousCancel.fireEvent(new ClickEvent() {
				});

			eventRowIndex = flex.getCellForEvent(event).getRowIndex();

			rbIdTxt.setText(flex.getText(eventRowIndex, 0));
			raavareIdTxt.setText(flex.getText(eventRowIndex, 1));
			maengdeTxt.setText(flex.getText(eventRowIndex, 2));

			// flex.setWidget(eventRowIndex, 0, rbIdTxt);
			flex.setWidget(eventRowIndex, 1, raavareIdTxt);
			flex.setWidget(eventRowIndex, 2, maengdeTxt);

			rbIdTxt.setFocus(true);

			final Anchor edit = (Anchor) event.getSource();

			final String rbId = rbIdTxt.getText();
			final String raavareId = raavareIdTxt.getText();
			final String maengde = maengdeTxt.getText();

			ok = new Anchor("ok");
			ok.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					flex.setText(eventRowIndex, 0, rbIdTxt.getText());
					flex.setText(eventRowIndex, 1, raavareIdTxt.getText());
					flex.setText(eventRowIndex, 2, maengdeTxt.getText());

					RaavareBatchDTO RB = new RaavareBatchDTO(Integer.parseInt(rbIdTxt.getText()),
							Integer.parseInt(raavareIdTxt.getText()), Double.parseDouble(maengdeTxt.getText()));

					client.service.updateRB(Raavarebatch.this.token, RB, new AsyncCallback<Void>() {

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

					rbIdTxt.setText(rbId);
					rbIdTxt.fireEvent(new KeyUpEvent() {
					});

					raavareIdTxt.setText(raavareId);
					raavareIdTxt.fireEvent(new KeyUpEvent() {
					}); // validation

					maengdeTxt.setText(maengde);
					maengdeTxt.fireEvent(new KeyUpEvent() {
					}); // validation

					flex.setText(eventRowIndex, 0, rbId);
					flex.setText(eventRowIndex, 1, raavareId);
					flex.setText(eventRowIndex, 2, maengde);
					// restore edit link
					flex.setWidget(eventRowIndex, 3, edit);
					flex.clearCell(eventRowIndex, 4);

					previousCancel = null;
				}

			});

			rbIdTxt.addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (!FieldVerifier.isValidRbId(raavareIdTxt.getText())) {
						raavareIdTxt.setStyleName("gwt-TextBox-invalidEntry");
						raavareIdValid = false;
					} else {
						raavareIdTxt.removeStyleName("gwt-TextBox-invalidEntry");
						raavareIdValid = true;
					}
					checkFormValid_Create();
					checkFormValid();
					
				}

			});
			raavareIdTxt.addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (!FieldVerifier.isValidRbId(raavareIdTxt.getText())) {
						raavareIdTxt.setStyleName("gwt-TextBox-invalidEntry");
						raavareIdValid = false;
					} else {
						raavareIdTxt.removeStyleName("gwt-TextBox-invalidEntry");
						raavareIdValid = true;
					}
						checkFormValid();
					
				}

			});
			
			maengdeTxt.addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (!FieldVerifier.isValidMaengde(maengdeTxt.getText())) {
						maengdeTxt.setStyleName("gwt-TextBox-invalidEntry");
						maengdeValid = false;
					} else {
						maengdeTxt.removeStyleName("gwt-TextBox-invalidEntry");
						maengdeValid = true;
					}
					checkFormValid_Create();
					checkFormValid();
					
				}

			});
			
			flex.setWidget(eventRowIndex, 3, ok);
			flex.setWidget(eventRowIndex, 4, cancel);
		}

		
	}


	
	private void checkFormValid_Create() {
		if (RbIdVaild && maengde){
			create.setEnabled(true);
		}
		else create.setEnabled(false);
			
		}
	
	
	private void checkFormValid() {
		if (rbIdValid && raavareIdValid && maengdeValid)

			flex.setWidget(eventRowIndex, 3, ok);

		else if (!(eventRowIndex == 0)){
			flex.setText(eventRowIndex, 3, "ok");
		}

	}
}
