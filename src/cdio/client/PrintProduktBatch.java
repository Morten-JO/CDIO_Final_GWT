package cdio.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import cdio.client.service.ServiceClientImpl;
import cdio.shared.ProduktBatchDTO;
import cdio.shared.RaavareDTO;
import cdio.shared.ReceptKompDTO;

public class PrintProduktBatch extends Composite {

	private final ServiceClientImpl client;
	private Label printedOn;
	private Label productBatchNr;
	private Label receptNr;
	private Label raavareNumber;
	private Label raavareName;
	private Label dashes;
	private Label attributes;
	private Label values;
	private Label sumTara;
	private Label sumNetto;
	private Label productionStatus;
	private Label productionStartedDate;
	private Label productionEndDate;
	private VerticalPanel vPanel;
	private ProduktBatchDTO PBResult;
	private List<ReceptKompDTO> receptList;
	private int incrementer = 0;
	private int pbNumber = 0;
	private String onToken;
	private ServiceClientImpl onClient;
	private Date d = new Date();
	private String date = d.getDate() + "-" + (1 + d.getMonth()) + "-" + (1900 + d.getYear());

	public PrintProduktBatch(ServiceClientImpl client, int produktNumber, String token) {
		this.onToken = token;
		this.onClient = client;
		pbNumber = produktNumber;
		this.client = client;

		vPanel = new VerticalPanel();
		initWidget(vPanel);
		client.service.getSpecificPB(produktNumber, token, new AsyncCallback<ProduktBatchDTO>() {

			@Override
			public void onSuccess(ProduktBatchDTO result) {
				PBResult = result;
				printedOn = new Label("Udskrevet      " + date);
				productBatchNr = new Label("Produkt Batch nr. " + pbNumber);
				receptNr = new Label("Recept nr. " + result.getReceptId());
				String status = "";
				if (result.getStatus() == 0) {
					status = "ikke startet";
				} else if (result.getStatus() == 1) {
					status = "startet";
				} else {
					status = "færdigt";
				}
				productionStatus = new Label("Produktion Status: " + status);
				if (result.getStartidspunkt() != null) {
					productionStartedDate = new Label("Produktion Startet:       " + result.getStartidspunkt());
				} else {
					productionStartedDate = new Label("Produktion Startet:       ");
				}
				if (result.getSluttidspunkt() != null) {
					productionEndDate = new Label("Produktion Slut:       " + result.getSluttidspunkt());
				} else {
					productionEndDate = new Label("Produktion Slut:       ");
				}
				vPanel.add(printedOn);
				vPanel.add(productBatchNr);
				receptNr.setStyleName("Print_LineHeight");
				vPanel.add(receptNr);

				if (PBResult != null) {
					onClient.service.getReceptKompsFromReceptID(PBResult.getReceptId(), onToken,
							new AsyncCallback<List<ReceptKompDTO>>() {

								@Override
								public void onFailure(Throwable caught) {
									Window.alert(""+ caught.getMessage());
								}

								@Override
								public void onSuccess(List<ReceptKompDTO> result) {
									receptList = result;
									if (receptList != null) {
										for (int i = 0; i < receptList.size(); i++) {
											onClient.service.getRaavareFromID(receptList.get(i).getRaavareId(), onToken,
													new AsyncCallback<RaavareDTO>() {
														@Override
														public void onFailure(Throwable caught) {
														}

														@Override
														public void onSuccess(RaavareDTO result) {
															vPanel.add(new HTML("<br><br>"));
															raavareNumber = new Label("Råvare id.        "
																	+ receptList.get(incrementer).getRaavareId());
															vPanel.add(raavareNumber);
															raavareName = new Label(
																	"Råvare navn:        " + result.getRaavareNavn());
															vPanel.add(raavareName);
															vPanel.add(new Label(
																	"-------------------------------------------------------------------------"));
															FlexTable flex = new FlexTable();
															flex.setText(0, 0, "Del");
															flex.setText(0, 1, "Mængde");
															flex.setText(0, 2, "Tolerance");
															flex.setText(0, 3, "Tara");
															flex.setText(0, 4, "Netto(kg)");
															flex.setText(0, 5, "Batch");
															flex.setText(0, 6, "Opr.");
															flex.setText(0, 7, "Terminal");

															flex.setText(1, 0, "1");
															flex.setText(1, 1,
																	"" + receptList.get(incrementer).getNomNetto());
															flex.setText(1, 2,
																	"" + receptList.get(incrementer).getTolerance());
															if (PBResult.getStatus() == 0) {
																flex.setText(1, 3, "");
																flex.setText(1, 4, "");
																flex.setText(1, 5, "");
																flex.setText(1, 6, "");
																flex.setText(1, 7, "");
															} else {
																flex.setText(1, 3, "B");
																flex.setText(1, 4, "B");
																flex.setText(1, 5, "B");
																flex.setText(1, 6, "U");
																flex.setText(1, 7, "1");
															}

															vPanel.add(flex);
															incrementer++;
															if (incrementer == receptList.size()) {
																vPanel.add(new HTML("<br><br>"));
																sumTara = new Label("Sum Tara: ");
																sumNetto = new Label("Sum Netto: ");
																vPanel.add(sumTara);
																vPanel.add(sumNetto);
																vPanel.add(new Label(""));

																vPanel.add(productionStatus);
																vPanel.add(productionStartedDate);
																vPanel.add(productionEndDate);

																print(vPanel.asWidget().getElement().getInnerHTML());
															}
														}
													});
										}
									}
								}
							});
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(""+caught.getMessage());
				printedOn = new Label("Udskrevet      FEJL");
				productBatchNr = new Label("Produkt Batch nr. " + pbNumber);
				receptNr = new Label("Recept nr. FEJL");
				productionStatus = new Label("Produktion Status: FEJL");
				productionStartedDate = new Label("Produktion Startet:       FEJL");
				productionStartedDate = new Label("Produktion Slut:       FEJL");

			}
		});

	}

	// pop ups in browser must be allowed
	public static final native void print(String html) /*-{
														top.consoleRef=$wnd.open('','_blank',"");
														top.consoleRef.document.write(html);
														top.consoleRef.print();
														top.consoleRef.document.close();
														}-*/;
}
