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
import com.sun.java.swing.plaf.windows.resources.windows;

import cdio.client.service.ServiceClientImpl;
import cdio.shared.FieldVerifier;
import cdio.shared.RaavareBatchDTO;
import cdio.shared.ReceptDTO;
import sun.print.resources.serviceui;
import cdio.shared.ReceptDTO;

public class Recept extends Composite {

	private FlexTable flex;
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private String token;
	private ServiceClientImpl client;

	TextBox recIdTxt;
	TextBox recNameTxt;
	TextBox addRecIdTxt;
	TextBox addRecNameTxt;
	
	
	boolean addRecIdValid = false;
	boolean addRecNavnValid = false;
	
	boolean recIdValid = false;
	boolean recNavnValid = false;
	

	Button create ;

	int eventRowIndex;
	Anchor ok;
	Anchor previousCancel = null;

	public Recept(ServiceClientImpl client, String token) {
		flex = new FlexTable();
		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		Label oprtRec = new Label("Opret Recept: ");
		oprtRec.setStyleName("Font-RB");
		initWidget(vPanel);
		this.token = token;
		this.client = client;
		
		addRecIdTxt = new TextBox();
		addRecIdTxt.setStyleName("TextBox-style");
		addRecNameTxt = new TextBox();
		addRecNameTxt.setHeight("20px");
		addRecNameTxt.setStyleName("TextBox-style");
		
		Label RecId = new Label("ReceptID : ");
		Label Recept = new Label("ReceptNavn : ");
		create = new Button("Create");
		//create.setStyleName("createbtn");
		create.setEnabled(false);
		vPanel.add(oprtRec);
		hPanel.add(RecId);
		hPanel.add(addRecIdTxt);
		hPanel.add(Recept);
		hPanel.add(addRecNameTxt);

		hPanel.add(create);
		vPanel.add(hPanel);
		
		addRecIdTxt.addKeyUpHandler(new KeyUpHandler(){

			@Override
			public void onKeyUp(KeyUpEvent event) {
				Recept.this.client.service.getRecept(Recept.this.token, new AsyncCallback<List<ReceptDTO>>(){
					@Override
					public void onFailure(Throwable caught) {
					}

					@Override
					public void onSuccess(List<ReceptDTO> result) {
						boolean idExists = false;
						try{
							Integer.parseInt(addRecIdTxt.getText());
							for(int i = 0; i < result.size(); i++){
								if(result.get(i).getReceptId() == Integer.parseInt(addRecIdTxt.getText())){
									idExists = true;
									
								}
							}
							if(!idExists){
								addRecIdTxt.removeStyleName("gwt-TextBox-invalidEntry");
								addRecIdValid = true;
								//failOprIDLbl.setText("");
							}
							else{
								addRecIdTxt.setStyleName("gwt-TextBox-invalidEntry");
								addRecIdValid = false;
								//failOprIDLbl.setText("Optaget id!");
							}
						} catch(NumberFormatException e){
							addRecIdTxt.setStyleName("gwt-TextBox-invalidEntry");
							addRecIdValid = false;
							//failOprIDLbl.setText("Optaget id!");
						}
						
					}
					
				});
				if (!FieldVerifier.isValidRbId(addRecIdTxt.getText())) {
					addRecIdTxt.setStyleName("gwt-TextBox-invalidEntry");
					addRecIdValid = false;
				} else {
					addRecIdTxt.removeStyleName("gwt-TextBox-invalidEntry");
					addRecIdValid = true;
				}
				checkFormValid_Create();
			}

		});
		
		addRecNameTxt.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				Recept.this.client.service.checkRecept(Recept.this.token, addRecNameTxt.getText(), new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
							boolean recExists = result;
						
						if (!FieldVerifier.isValidName(addRecNameTxt.getText()) || recExists) {
							addRecNameTxt.setStyleName("gwt-TextBox-invalidEntry");
							addRecNavnValid = false;
						} else {
							addRecNameTxt.removeStyleName("gwt-TextBox-invalidEntry");
							addRecNavnValid = true;
						}
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});

				checkFormValid_Create();
				
				
			}

		});
		
		create.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int recId = Integer.parseInt(addRecIdTxt.getText());
				String recName = addRecNameTxt.getText();
				
				
				
				ReceptDTO RB = new ReceptDTO(recId, recName);
				Recept.this.client.service.createRecept(Recept.this.token, RB, new AsyncCallback<Void>(){
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Server fejl!" + caught.getMessage());
							
						}

						@Override
						public void onSuccess(Void result) {
							Window.alert("Recept er nu gemt");
							addRecIdTxt.setText("");
							addRecNameTxt.setText("");
							checkFormValid();
							create.setEnabled(false);
							Window.Location.reload();
						}

					});
				}
			});
		
		flex.addStyleName("FlexTable");
		flex.getRowFormatter().addStyleName(0,"FlexTable-Header");
		
		client.service.getRecept(token, new AsyncCallback<List<ReceptDTO>>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(List<ReceptDTO> result) {

				flex.setText(0, 0, "ReceptId");
				flex.setText(0, 1, "Navn");
				;

				for (int rowIndex = 0; rowIndex < result.size(); rowIndex++) {

					flex.setText(rowIndex + 1, 0, "" + result.get(rowIndex).getReceptId());
					flex.setText(rowIndex + 1, 1, "" + result.get(rowIndex).getReceptNavn());
					
					flex.getCellFormatter().addStyleName(rowIndex+1, 0, "FlexTable-Cell");
					flex.getCellFormatter().addStyleName(rowIndex+1, 1, "FlexTable-Cell");

					
					
					Anchor edit = new Anchor("edit");
					flex.setWidget(rowIndex + 1, 2, edit);

					edit.addClickHandler(new EditHandler());
					
				}

				// flex.setStyleName("FlexTable");

			}

		});
		


		vPanel.add(flex);
		// create textboxs
		recIdTxt = new TextBox();
		recIdTxt.setWidth("20px");
		recNameTxt = new TextBox();
		recNameTxt.setWidth("60px");


	}

	private class EditHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			if (previousCancel != null)
				previousCancel.fireEvent(new ClickEvent() {
				});

			eventRowIndex = flex.getCellForEvent(event).getRowIndex();

			recIdTxt.setText(flex.getText(eventRowIndex, 0));
			recNameTxt.setText(flex.getText(eventRowIndex, 1));
			

			// flex.setWidget(eventRowIndex, 0, recIdTxt);
			flex.setWidget(eventRowIndex, 1, recNameTxt);
			

			recIdTxt.setFocus(true);

			final Anchor edit = (Anchor) event.getSource();

			final String rbId = recIdTxt.getText();
			final String raavareId = recNameTxt.getText();
			

			ok = new Anchor("ok");
			ok.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					flex.setText(eventRowIndex, 0, recIdTxt.getText());
					flex.setText(eventRowIndex, 1, recNameTxt.getText());
					

					ReceptDTO RB = new ReceptDTO(Integer.parseInt(recIdTxt.getText()),
							recNameTxt.getText());

					client.service.updateRecept(Recept.this.token, RB, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							System.out.println("nusnus");

						}

						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub

						}

					});
					flex.setWidget(eventRowIndex, 2, edit);
					flex.clearCell(eventRowIndex, 3);

					previousCancel = null;

				}
			});

			Anchor cancel = new Anchor("cancel");
			previousCancel = cancel;
			cancel.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					recIdTxt.setText(rbId);
					recIdTxt.fireEvent(new KeyUpEvent() {
					});

					recNameTxt.setText(raavareId);
					recNameTxt.fireEvent(new KeyUpEvent() {
					}); // validation



					flex.setText(eventRowIndex, 0, rbId);
					flex.setText(eventRowIndex, 1, raavareId);

					// restore edit link
					flex.setWidget(eventRowIndex, 2, edit);
					flex.clearCell(eventRowIndex, 3);

					previousCancel = null;
				}

			});

			recIdTxt.addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {

					if (!FieldVerifier.isValidRbId(recIdTxt.getText())) {
						recIdTxt.setStyleName("gwt-TextBox-invalidEntry");
						recIdValid = false;
					} else {
						recIdTxt.removeStyleName("gwt-TextBox-invalidEntry");
						recIdValid = true;
					}
					checkFormValid();
					;
				}

			});

			recNameTxt.addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (!FieldVerifier.isValidName(recNameTxt.getText())) {
						recNameTxt.setStyleName("gwt-TextBox-invalidEntry");
						recNavnValid = false;
					} else {
						recNameTxt.removeStyleName("gwt-TextBox-invalidEntry");
						recNavnValid = true;
					}
					checkFormValid();
					;
				}

			});



			flex.setWidget(eventRowIndex, 2, ok);
			flex.setWidget(eventRowIndex, 3, cancel);
		}
	}

	private void checkFormValid() {
		if (recNavnValid && recIdValid)

			flex.setWidget(eventRowIndex, 2, ok);

		else
			flex.setText(eventRowIndex, 2, "ok");

	}
	
	
	private void checkFormValid_Create() {
		if (addRecIdValid && addRecNavnValid){
			create.setEnabled(true);
		}
		else create.setEnabled(false);
			
		}
	
}