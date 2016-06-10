package cdio.client;

import java.sql.Timestamp;
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
import cdio.shared.ProduktBatchDTO;
import cdio.shared.ProduktBatchDTO;
import cdio.shared.ProduktBatchDTO;

public class Produktbatch extends Composite {

	private FlexTable flex;
	private VerticalPanel vPanel;
	private String token;
	private ServiceClientImpl client;

	TextBox pbIdTxt;
	TextBox receptIdTxt;
	TextBox statusTxt;

	boolean rbIdValid = true;
	boolean raavareIdValid = true;
	boolean maengdeValid = true;

	int eventRowIndex;
	Anchor ok;
	Anchor previousCancel = null;

	public Produktbatch(ServiceClientImpl client, String token) {
		flex = new FlexTable();
		vPanel = new VerticalPanel();
		initWidget(vPanel);
		this.token = token;
		this.client = client;

		client.service.getPB(token, new AsyncCallback<List<ProduktBatchDTO>>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(List<ProduktBatchDTO> result) {

				flex.setText(0, 0, "pbId");
				flex.setText(0, 1, "receptId");
				flex.setText(0, 2, "status");
				flex.setText(0, 3, "StartTidspunkt");
				flex.setText(0, 4, "SlutTidspunkt");

				for (int rowIndex = 0; rowIndex < result.size(); rowIndex++) {

					flex.setText(rowIndex + 1, 0, "" + result.get(rowIndex).getPbId());
					flex.setText(rowIndex + 1, 1, "" + result.get(rowIndex).getReceptId());
					flex.setText(rowIndex + 1, 2, "" + result.get(rowIndex).getStatus());
					flex.setText(rowIndex + 1, 3, "" + result.get(rowIndex).getStartidspunkt());
					flex.setText(rowIndex + 1, 4, "" + result.get(rowIndex).getSluttidspunkt());
					Anchor edit = new Anchor("edit");
					flex.setWidget(rowIndex + 1, 5, edit);

					edit.addClickHandler(new EditHandler());
				}

				// flex.setStyleName("FlexTable");

			}

		});
		vPanel.add(flex);
		// create textboxs
		pbIdTxt = new TextBox();
		pbIdTxt.setWidth("20px");
		receptIdTxt = new TextBox();
		receptIdTxt.setWidth("60px");
		statusTxt = new TextBox();
		statusTxt.setWidth("80px");

	}
	
	private class EditHandler implements ClickHandler {
		public void onClick(ClickEvent event) {

			if (previousCancel != null)
				previousCancel.fireEvent(new ClickEvent() {
				});

			eventRowIndex = flex.getCellForEvent(event).getRowIndex();

			pbIdTxt.setText(flex.getText(eventRowIndex, 0));
			receptIdTxt.setText(flex.getText(eventRowIndex, 1));
			statusTxt.setText(flex.getText(eventRowIndex, 2));

			// flex.setWidget(eventRowIndex, 0, pbIdTxt);
			flex.setWidget(eventRowIndex, 1, receptIdTxt);
			flex.setWidget(eventRowIndex, 2, statusTxt);

			pbIdTxt.setFocus(true);

			final Anchor edit = (Anchor) event.getSource();

			final String pbId = pbIdTxt.getText();
			final String receptId = receptIdTxt.getText();
			final String status = statusTxt.getText();

			ok = new Anchor("ok");
			ok.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					flex.setText(eventRowIndex, 0, pbIdTxt.getText());
					flex.setText(eventRowIndex, 1, receptIdTxt.getText());
					flex.setText(eventRowIndex, 2, statusTxt.getText());

					ProduktBatchDTO PB = new ProduktBatchDTO(Integer.parseInt(pbIdTxt.getText()),
							Integer.parseInt(receptIdTxt.getText()), Integer.parseInt(statusTxt.getText()), null, null);

					client.service.updatePB(Produktbatch.this.token, PB, new AsyncCallback<Void>() {

						@Override
						public void onFailure(Throwable caught) {
							System.out.println("nusnus");

						}

						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub

						}

					});
					flex.setWidget(eventRowIndex, 5, edit);
					flex.clearCell(eventRowIndex, 6);

					previousCancel = null;

				}
			});

			Anchor cancel = new Anchor("cancel");
			previousCancel = cancel;
			cancel.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {

					pbIdTxt.setText(pbId);
					pbIdTxt.fireEvent(new KeyUpEvent() {
					});

					receptIdTxt.setText(receptId);
					receptIdTxt.fireEvent(new KeyUpEvent() {
					}); // validation

					statusTxt.setText(status);
					statusTxt.fireEvent(new KeyUpEvent() {
					}); // validation

					flex.setText(eventRowIndex, 0, pbId);
					flex.setText(eventRowIndex, 1, receptId);
					flex.setText(eventRowIndex, 2, status);
					// restore edit link
					flex.setWidget(eventRowIndex, 5, edit);
					flex.clearCell(eventRowIndex, 6);

					previousCancel = null;
				}

			});

			pbIdTxt.addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {

					if (!FieldVerifier.isValidRbId(pbIdTxt.getText())) {
						pbIdTxt.setStyleName("gwt-TextBox-invalidEntry");
						rbIdValid = false;
					} else {
						pbIdTxt.removeStyleName("gwt-TextBox-invalidEntry");
						rbIdValid = true;
					}
					checkFormValid();
					;
				}

			});

			receptIdTxt.addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (!FieldVerifier.isValidRbId(receptIdTxt.getText())) {
						receptIdTxt.setStyleName("gwt-TextBox-invalidEntry");
						raavareIdValid = false;
					} else {
						receptIdTxt.removeStyleName("gwt-TextBox-invalidEntry");
						raavareIdValid = true;
					}
					checkFormValid();
					;
				}

			});

			statusTxt.addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (!FieldVerifier.isValidMaengde(statusTxt.getText())) {
						statusTxt.setStyleName("gwt-TextBox-invalidEntry");
						maengdeValid = false;
					} else {
						statusTxt.removeStyleName("gwt-TextBox-invalidEntry");
						maengdeValid = true;
					}
					checkFormValid();
					;
				}

			});

			flex.setWidget(eventRowIndex, 5, ok);
			flex.setWidget(eventRowIndex, 6, cancel);
		}
	}
	
	
	private void checkFormValid() {
		if (rbIdValid && raavareIdValid && maengdeValid)

			flex.setWidget(eventRowIndex, 5, ok);

		else
			flex.setText(eventRowIndex, 5, "ok");

	}

}