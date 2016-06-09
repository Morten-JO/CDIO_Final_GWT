package cdio.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio.client.service.ServiceClientImpl;
import cdio.shared.ReceptDTO;

public class Recept extends Composite {

	private FlexTable flex;
	private String token;
	private VerticalPanel vPanel;
	
	public Recept(ServiceClientImpl client, String token) {
		flex = new FlexTable();
		vPanel = new VerticalPanel();
		initWidget(vPanel);
		this.token = token;
		
		client.service.getRecept(token, new AsyncCallback<List<ReceptDTO>>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(List<ReceptDTO> result) {
				// TODO Auto-generated method stub
				flex.setText(0, 0, "receptId");
				flex.setText(0, 1, "receptNavn");
				

			    for(int i = 0; i < result.size(); i++){
			    	
			    		flex.setText(i+1, 0, ""+ result.get(i).getReceptId());
			    		flex.setText(i+1, 1, ""+ result.get(i).getReceptNavn());
			    		
			    }
			    
			    flex.setStyleName("FlexTable");
				
			}
			
			
		});
		vPanel.add(flex);
	}

}
