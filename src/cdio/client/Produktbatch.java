package cdio.client;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio.client.service.ServiceClientImpl;
import cdio.shared.FieldVerifier;
import cdio.shared.ProduktBatchDTO;
import cdio.shared.ProduktBatchKompDTO;
import cdio.shared.RaavareBatchDTO;
import cdio.shared.RaavareDTO;
import cdio.shared.ReceptDTO;
import cdio.shared.ReceptKompDTO;

public class Produktbatch extends Composite {

	private FlexTable flex;
	private VerticalPanel vPanel;
	private HorizontalPanel hPanel;
	private String token;
	private ServiceClientImpl client;

	TextBox AddPbId;
	ListBox AddReceptId;

	TextBox pbIdTxt;
	TextBox receptIdTxt;
	TextBox statusTxt;

	Button create;

	DialogBox produktBatchKomp = new DialogBox();

	boolean addPbValid = false;
	boolean statusValid = false;
	boolean rbIdValid = true;
	boolean receptIdValid = true;

	private List<ProduktBatchDTO> listOfPB;
	private int number;

	int eventRowIndex;
	Anchor ok;
	Anchor previousCancel = null;

	MainViewController con;

	public Produktbatch(ServiceClientImpl client, String token, MainViewController con) {
		this.con = con;
		flex = new FlexTable();
		flex.setStyleName("FlexTable");
		flex.getRowFormatter().addStyleName(0, "FlexTable-Header");
		vPanel = new VerticalPanel();
		hPanel = new HorizontalPanel();
		Label oprtPB = new Label("Opret Produktbatch :");
		oprtPB.setStyleName("Font-RB");
		initWidget(vPanel);
		this.token = token;
		this.client = client;

		AddPbId = new TextBox();
		AddPbId.setStyleName("TextBox-style");
		AddReceptId = new ListBox();
		AddReceptId.setHeight("20px");
		AddReceptId.setStyleName("TextBox-style");

		client.service.getRecept(token, new AsyncCallback<List<ReceptDTO>>() {

			@Override
			public void onSuccess(List<ReceptDTO> result) {
				for (int i = 0; i < result.size(); i++) {

					AddReceptId.addItem(Integer.toString(result.get(i).getReceptId()));
				}

			}

			@Override
			public void onFailure(Throwable caught) {

			}
		});

		Label PbId = new Label("PbId : ");
		Label Recept = new Label("ReceptId : ");

		create = new Button("Create");
		// create.setStyleName("createbtn");
		create.setEnabled(false);

		vPanel.add(oprtPB);
		hPanel.add(PbId);
		hPanel.add(AddPbId);
		hPanel.add(Recept);
		hPanel.add(AddReceptId);

		hPanel.add(create);
		vPanel.add(hPanel);

		// Check add functionality for field. Reason: validation
		AddPbId.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				Produktbatch.this.client.service.getPB(Produktbatch.this.token,
						new AsyncCallback<List<ProduktBatchDTO>>() {
							@Override
							public void onFailure(Throwable caught) {
							}

							@Override
							public void onSuccess(List<ProduktBatchDTO> result) {
								boolean idExists = false;
								try {
									Integer.parseInt(AddPbId.getText());
									for (int i = 0; i < result.size(); i++) {
										if (result.get(i).getPbId() == Integer.parseInt(AddPbId.getText())) {
											idExists = true;

										}
									}
									if (!idExists) {
										AddPbId.removeStyleName("gwt-TextBox-invalidEntry");
										addPbValid = true;
										// failOprIDLbl.setText("");
									} else {
										AddPbId.setStyleName("gwt-TextBox-invalidEntry");
										addPbValid = false;
										// failOprIDLbl.setText("Optaget id!");
									}
								} catch (NumberFormatException e) {
									AddPbId.setStyleName("gwt-TextBox-invalidEntry");
									addPbValid = false;
									// failOprIDLbl.setText("Optaget id!");
								}

							}

						});
				if (!FieldVerifier.isValidRbId(AddPbId.getText())) {
					AddPbId.setStyleName("gwt-TextBox-invalidEntry");
					addPbValid = false;
				} else {
					AddPbId.removeStyleName("gwt-TextBox-invalidEntry");
					addPbValid = true;
				}
				checkFormValid_Create();
			}

		});

		create.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				int pbId = Integer.parseInt(AddPbId.getText());
				int receptId = Integer.parseInt(AddReceptId.getSelectedItemText());

				ProduktBatchDTO RB = new ProduktBatchDTO(pbId, receptId, 0, null, null);
				Produktbatch.this.client.service.createPB(Produktbatch.this.token, RB, new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Server fejl!" + caught.getMessage());

					}

					@Override
					public void onSuccess(Void result) {
						Produktbatch.this.client.service.getPB(Produktbatch.this.token,
								new AsyncCallback<List<ProduktBatchDTO>>() {

									@Override
									public void onFailure(Throwable caught) {

									}

									@Override
									public void onSuccess(List<ProduktBatchDTO> result) {
										listOfPB = result;
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

											flex.getCellFormatter().addStyleName(rowIndex + 1, 0, "FlexTable-Cell");
											flex.getCellFormatter().addStyleName(rowIndex + 1, 1, "FlexTable-Cell");
											flex.getCellFormatter().addStyleName(rowIndex + 1, 2, "FlexTable-Cell");
											flex.getCellFormatter().addStyleName(rowIndex + 1, 3, "FlexTable-Cell");
											flex.getCellFormatter().addStyleName(rowIndex + 1, 4, "FlexTable-Cell");

											Anchor edit = new Anchor("edit");
											flex.setWidget(rowIndex + 1, 5, edit);
											Anchor print = new Anchor("print");
											flex.setWidget(rowIndex + 1, 7, print);
											Anchor addKomponent = new Anchor("Tilf\u00F8j Komponent");
											flex.setWidget(rowIndex + 1, 8, addKomponent);
											addKomponent.addClickHandler(new KomponentHandler());
											print.addClickHandler(new PrintHandler());

											edit.addClickHandler(new EditHandler());
										}

										// flex.setStyleName("FlexTable");

									}

								});

						Window.alert("Produktbatchbatch er nu gemt");
						AddPbId.setText("");
						checkFormValid();
						create.setEnabled(false);

					}

				});
			}
		});

		client.service.getPB(token, new AsyncCallback<List<ProduktBatchDTO>>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(List<ProduktBatchDTO> result) {
				listOfPB = result;
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

					flex.getCellFormatter().addStyleName(rowIndex + 1, 0, "FlexTable-Cell");
					flex.getCellFormatter().addStyleName(rowIndex + 1, 1, "FlexTable-Cell");
					flex.getCellFormatter().addStyleName(rowIndex + 1, 2, "FlexTable-Cell");
					flex.getCellFormatter().addStyleName(rowIndex + 1, 3, "FlexTable-Cell");
					flex.getCellFormatter().addStyleName(rowIndex + 1, 4, "FlexTable-Cell");

					Anchor edit = new Anchor("edit");
					flex.setWidget(rowIndex + 1, 5, edit);

					edit.addClickHandler(new EditHandler());
					Anchor print = new Anchor("print");
					flex.setWidget(rowIndex + 1, 7, print);
					print.addClickHandler(new PrintHandler());
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

	private class PrintHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			eventRowIndex = flex.getCellForEvent(event).getRowIndex();
			Produktbatch.this.con.showPrintProduktBatch(listOfPB.get(eventRowIndex - 1).getPbId());
			// Window.print();
			// new MainViewController(client,
			// token).showPrintProduktBatch(number);
			// RootPanel.get().clear();
			// RootPanel.get().add(new PrintProduktBatch(client, number,
			// token));
		}

	}

	private class EditHandler implements ClickHandler {
		@Override
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
						receptIdValid = false;
					} else {
						receptIdTxt.removeStyleName("gwt-TextBox-invalidEntry");
						receptIdValid = true;
					}
					checkFormValid();
					;
				}

			});

			statusTxt.addKeyUpHandler(new KeyUpHandler() {

				@Override
				public void onKeyUp(KeyUpEvent event) {
					if (!FieldVerifier.isValidStatus(statusTxt.getText())) {
						statusTxt.setStyleName("gwt-TextBox-invalidEntry");
						statusValid = false;
					} else {
						statusTxt.removeStyleName("gwt-TextBox-invalidEntry");
						statusValid = true;
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
		if (rbIdValid && statusValid && receptIdValid)

			flex.setWidget(eventRowIndex, 5, ok);

		else
			flex.setText(eventRowIndex, 5, "ok");

	}

	private void checkFormValid_Create() {
		if (addPbValid && receptIdValid) {
			create.setEnabled(true);
		} else
			create.setEnabled(false);

	}

	// Klasse til administration af Produktbatchkomponenter
	private class KomponentHandler implements ClickHandler {

		private ListBox produktBatchlist;
		private ListBox raavareBatchlist;
		private FlexTable raavareBatches;
		private boolean validRaavareBatch = false;
		private boolean validProduktBatch = false;
		private Button addKomp;
		TextBox raavareBatchTxt;
		TextBox produktBatchTxt;
		private int produktBatchSelected;
		private int column;
		ArrayList<String> raavareBatchesIRec;

		@Override
		public void onClick(ClickEvent event) {
			produktBatchSelected = Integer.parseInt(flex.getText(flex.getCellForEvent(event).getRowIndex(), 0));
			column = flex.getCellForEvent(event).getRowIndex();
			produktBatchKomp = new DialogBox();
			produktBatchKomp.center();

			raavareBatches = new FlexTable();
			// raavareBatchesIRec = new ArrayList<String>();
			raavareBatches.setText(0, 0, "Raavarebatch Id:");
			raavareBatches.setStyleName("FlexTable-Dialog");

			HorizontalPanel kompPanel = new HorizontalPanel();
			VerticalPanel verKompPanel = new VerticalPanel();

			Label Navn = new Label("raavareBatches i " + flex.getText(flex.getCellForEvent(event).getRowIndex(), 1));
			Navn.setStyleName("Dialog-Text");

			Label netto = new Label("Raavarebatch");
			Label tolerance = new Label("Produktbatch");

			// produktBatchTxt = new TextBox();
			// raavareBatchTxt = new TextBox();
			// produktBatchTxt.setStyleName("TextBox-style");
			// raavareBatchTxt.setStyleName("TextBox-style");
			//
			// raavareBatchTxt.addKeyUpHandler(new KeyUpHandler() {
			//
			// @Override
			// public void onKeyUp(KeyUpEvent event) {
			//
			// if (!FieldVerifier.isValidRbId(raavareBatchTxt.getText())) {
			// raavareBatchTxt.setStyleName("gwt-TextBox-invalidEntry");
			// validRaavareBatch = false;
			// } else {
			// raavareBatchTxt.removeStyleName("gwt-TextBox-invalidEntry");
			// validRaavareBatch = true;
			// }
			// checkFormValidKomp();
			//
			// }
			//
			// });
			//
			// produktBatchTxt.addKeyUpHandler(new KeyUpHandler() {
			//
			// @Override
			// public void onKeyUp(KeyUpEvent event) {
			//
			// if (!FieldVerifier.isValidRbId(produktBatchTxt.getText())) {
			// produktBatchTxt.setStyleName("gwt-TextBox-invalidEntry");
			// validProduktBatch = false;
			// } else {
			// produktBatchTxt.removeStyleName("gwt-TextBox-invalidEntry");
			// validProduktBatch= true;
			// }
			// checkFormValidKomp();
			//
			// }
			//
			// });

			addKomp = new Button("Tilf\u00F8j denne komponent");
			addKomp.setEnabled(false);
			addKomp.addClickHandler(new ClickHandler() {

				// Tilf�j ny receptkomponent
				@Override
				public void onClick(ClickEvent event) {
					// Window.alert("item selected: " +
					// addlist.getSelectedItemText());

					client.service.createPBKomp(new ProduktBatchKompDTO(
							Integer.parseInt(KomponentHandler.this.produktBatchlist.getSelectedItemText()),
							Integer.parseInt(KomponentHandler.this.raavareBatchlist.getSelectedItemText()), 0, 0, 0),
							new AsyncCallback<Void>() {

								@Override
								public void onSuccess(Void result) {
									// TODO Auto-generated method stub

								}

								@Override
								public void onFailure(Throwable caught) {
									// TODO Auto-generated method stub

								}
							});

				}
			});

			Button close = new Button("Luk Vindue	");

			close.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					recKomp.hide();

				}
			});

			addlist = new ListBox();

			// Hent data fra database til felxtable/listbxox
			client.service.getRaavIRec(recIdSelected, new AsyncCallback<List<String>>() {

				@Override
				public void onSuccess(List<String> result) {
					// Window.alert(result.get(1));
					try {
						for (int i = 0; i < result.size(); i++) {
							raavareBatches.setText(i + 1, 0, result.get(i));
							raavareBatchesIRec.add(result.get(i));

						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Add elements to list
					client.service.getRaavare(token, new AsyncCallback<List<RaavareDTO>>() {

						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Fejl raav");

						}

						@Override
						public void onSuccess(List<RaavareDTO> result) {

							int count = 0;
							for (int i = 0; i < result.size(); i++) {

								boolean addable = true;

								{
									for (int j = 0; j < raavareBatchesIRec.size(); j++) {
										if (result.get(i).getRaavareNavn()
												.equalsIgnoreCase(raavareBatchesIRec.get(j))) {
											addable = false;
										}

									}
								}
								if (addable)
									addlist.addItem(result.get(i).getRaavareNavn());

							}
						}

					});

				}

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Fejl raavirec");

				}
			});

			kompPanel.add(Navn);
			kompPanel.setStyleName("Dialog");
			verKompPanel.add(kompPanel);
			verKompPanel.add(raavareBatches);
			verKompPanel.add(netto);
			verKompPanel.add(nettoTxt);
			verKompPanel.add(tolerance);
			verKompPanel.add(toleranceTxt);

			verKompPanel.add(addlist);
			verKompPanel.add(addKomp);

			verKompPanel.add(close);

			recKomp.add(verKompPanel);
			recKomp.show();

		}

		private void checkFormValidKomp() {
			if (validNetto && validTolerance)

				addKomp.setEnabled(true);

			else
				addKomp.setEnabled(false);

		}
	}

}