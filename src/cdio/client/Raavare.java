package cdio.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


import cdio.client.service.ServiceClientImpl;
import cdio.shared.FieldVerifier;
import cdio.shared.ProduktBatchDTO;
import cdio.shared.RaavareBatchDTO;
import cdio.shared.RaavareDTO;

public class Raavare extends Composite{


	private FlexTable flex;
	private VerticalPanel vPanel;
	private String token;
	private ServiceClientImpl client;

	TextBox raavIdTxt;
	TextBox raavNavnTxt;
	TextBox leverandoerTxt;

	boolean raavIdValid = true;
	boolean raavNavnValid = true;
	boolean leverandoerValid = true;

	int eventRowIndex;
	Anchor ok;
	Anchor previousCancel = null;

	public Raavare(ServiceClientImpl client, String token) {
		flex = new FlexTable();
		flex.setStyleName("FlexTable");
		flex.getRowFormatter().addStyleName(0,"FlexTable-Header");
		vPanel = new VerticalPanel();
		initWidget(vPanel);
		this.token = token;
		this.client = client;

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
}