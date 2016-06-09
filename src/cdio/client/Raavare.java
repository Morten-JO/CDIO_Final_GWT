package cdio.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio.client.service.ServiceClientImpl;
import cdio.shared.ProduktBatchDTO;
import cdio.shared.RaavareDTO;

public class Raavare extends Composite{

	private FlexTable flex;
	private String token;
	private VerticalPanel vPanel;
	
	
	public Raavare(ServiceClientImpl client, String token){
		flex = new FlexTable();
		vPanel = new VerticalPanel();
		initWidget(vPanel);
		this.token = token;
		
		client.service.getRaavare(token, new AsyncCallback<List<RaavareDTO>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<RaavareDTO> result) {
				flex.setText(0, 0, "raavareId");
				flex.setText(0, 1, "RaavareNavn");
				flex.setText(0, 2, "leverandoer");
				
				 for(int i = 0; i < result.size(); i++){
				    	
			    		flex.setText(i+1, 0, ""+ result.get(i).getRaavareId());
			    		flex.setText(i+1, 1, ""+ result.get(i).getRaavareNavn());
			    		flex.setText(i+1, 2, ""+ result.get(i).getLeverandoer());
				
			}
				 flex.setStyleName("FlexTable");
		}
		});
	
				 vPanel.add(flex);
}
}
