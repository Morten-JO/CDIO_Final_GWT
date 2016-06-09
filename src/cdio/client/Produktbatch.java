package cdio.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio.client.service.ServiceClientImpl;
import cdio.shared.ProduktBatchDTO;
import cdio.shared.RaavareBatchDTO;

public class Produktbatch extends Composite {
	private FlexTable flex;
	private String token;
	private VerticalPanel vPanel;

	public Produktbatch(ServiceClientImpl client,String token) {
		flex = new FlexTable();
		vPanel = new VerticalPanel();
		initWidget(vPanel);
		this.token = token;
		
	 client.service.getPB(token, new AsyncCallback<List<ProduktBatchDTO>>(){

		@Override
		public void onFailure(Throwable caught) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onSuccess(List<ProduktBatchDTO> result) {
			flex.setText(0, 0, "pbId");
			flex.setText(0, 1, "receptId");
			flex.setText(0, 2, "status");
			flex.setText(0, 3, "startTidspunkt");
			flex.setText(0, 4, "slutTidspunkt");

		    for(int i = 0; i < result.size(); i++){
		    	
		    		flex.setText(i+1, 0, ""+ result.get(i).getPbId());
		    		flex.setText(i+1, 1, ""+ result.get(i).getReceptId());
		    		flex.setText(i+1, 2, ""+ result.get(i).getStatus());
		    	//	flex.setText(i+1, 3, ""+result.get(i).getStartidspunkt());
		    	//	flex.setText(i+1, 3, ""+result.get(i).getSluttidspunkt());
		    }
		    
		    flex.setStyleName("FlexTable");
			
		}
		 
	 });
	 vPanel.add(flex);
	}

}
