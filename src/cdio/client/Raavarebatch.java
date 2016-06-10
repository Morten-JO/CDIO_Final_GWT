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
import cdio.shared.FieldVerifier;

import cdio.client.service.ServiceClientImpl;
import cdio.shared.RaavareDTO;

import cdio.shared.RaavareBatchDTO;

public class Raavarebatch extends Composite {

	private FlexTable flex;
	private VerticalPanel vPanel;
	private String token;
	private ServiceClientImpl client;

	TextBox rbIdTxt;
	TextBox raavareIdTxt;
	TextBox maengdeTxt;

	boolean rbIdValid = true;
	boolean raavareIdValid = true;
	boolean maengdeValid = true;

	int eventRowIndex;
	Anchor ok;
	Anchor previousCancel = null;

	public Raavarebatch(ServiceClientImpl client, String token) {
		flex = new FlexTable();
		vPanel = new VerticalPanel();
		initWidget(vPanel);
		this.token = token;
		this.client = client;

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

					if (!FieldVerifier.isValidRbId(rbIdTxt.getText())) {
						rbIdTxt.setStyleName("gwt-TextBox-invalidEntry");
						rbIdValid = false;
					} else {
						rbIdTxt.removeStyleName("gwt-TextBox-invalidEntry");
						rbIdValid = true;
					}
					checkFormValid();
					;
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
					;
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
					checkFormValid();
					;
				}

			});

			flex.setWidget(eventRowIndex, 3, ok);
			flex.setWidget(eventRowIndex, 4, cancel);
		}
	}

	private void checkFormValid() {
		if (rbIdValid && raavareIdValid && maengdeValid)

			flex.setWidget(eventRowIndex, 3, ok);

		else
			flex.setText(eventRowIndex, 3, "ok");

	}
}
