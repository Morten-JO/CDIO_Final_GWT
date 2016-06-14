package cdio.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

import com.google.gwt.user.client.ui.VerticalPanel;
import cdio.client.service.ServiceClientImpl;

import cdio.shared.UserDTO;

public class UserView extends Composite {

	private FlexTable flex;
	private VerticalPanel vPanel;
	private String token;
	private ServiceClientImpl client;

	

	int eventRowIndex;

	public UserView(ServiceClientImpl client, String token) {
		flex = new FlexTable();
		flex.setStyleName("FlexTable");
		flex.getRowFormatter().addStyleName(0,"FlexTable-Header");
		vPanel = new VerticalPanel();
		initWidget(vPanel);
		this.token = "admin";
		this.client = client;
		

		client.service.getPersons(token, new AsyncCallback<List<UserDTO>>() {

			@Override
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(List<UserDTO> result) {
				
				flex.setText(0, 0, "Operator ID");
				flex.setText(0, 1, "Navn");
				flex.setText(0, 2, "Initialer");
				flex.setText(0, 3, "Rolle");

				for (int rowIndex = 0; rowIndex < result.size(); rowIndex++) {

					flex.setText(rowIndex + 1, 0, "" + result.get(rowIndex).getOprId());
					flex.setText(rowIndex + 1, 1, "" + result.get(rowIndex).getNavn());
					flex.setText(rowIndex + 1, 2, "" + result.get(rowIndex).getIni());
					flex.setText(rowIndex + 1, 3, "" + result.get(rowIndex).getRolle());
					
					flex.getCellFormatter().addStyleName(rowIndex+1, 0, "FlexTable-Cell");
					flex.getCellFormatter().addStyleName(rowIndex+1, 1, "FlexTable-Cell");
					flex.getCellFormatter().addStyleName(rowIndex+1, 2, "FlexTable-Cell");
					flex.getCellFormatter().addStyleName(rowIndex+1, 3, "FlexTable-Cell");


				}


			}

		});
		vPanel.add(flex);

	}
	

}