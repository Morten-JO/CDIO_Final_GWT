package cdio.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;


import cdio.client.service.ServiceClientImpl;
import cdio.shared.FieldVerifier;
import cdio.shared.ReceptDTO;
import cdio.shared.ReceptDTO;

public class Recept extends Composite {

	private FlexTable flex;
	private VerticalPanel vPanel;
	private String token;
	private ServiceClientImpl client;

	TextBox recIdTxt;
	TextBox recNameTxt;
	

	boolean recIdValid = true;
	boolean recNavnValid = true;
	

	int eventRowIndex;
	Anchor ok;
	Anchor previousCancel = null;

	public Recept(ServiceClientImpl client, String token) {
		flex = new FlexTable();
		vPanel = new VerticalPanel();
		initWidget(vPanel);
		this.token = token;
		this.client = client;

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
}