package cdio.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio.client.service.ServiceClientImpl;
import cdio.shared.RaavareBatchDTO;
import cdio.shared.UserDTO;

public class Raavarebatch extends Composite  {

	
		private FlexTable flex;

		private VerticalPanel vPanel;
		
		
		public Raavarebatch(ServiceClientImpl client) {
			flex = new FlexTable();
			vPanel = new VerticalPanel();
			initWidget(vPanel);
			
			//lbl.setStyleName("Header-Text");
			//this.vPanel.add(lbl);
			
			client.service.getRaavareBatches(new AsyncCallback<List<RaavareBatchDTO>>() {
				
			
		
				@Override
				public void onFailure(Throwable caught) {
					//ShowPersons.this.lbl.setText("jensen");
					
				}

				@Override
				public void onSuccess(List<RaavareBatchDTO> result) {
				//	ShowPersons.this.lbl.setText("nusnus");
//					delete = new Button[result.size()];
//					change = new Button[result.size()];
					
					flex.setText(0, 0, "rbId");
					flex.setText(0, 1, "raavareNavn");
					flex.setText(0, 2, "maengde");

				    for(int i = 0; i < result.size(); i++){
				    	
				    		flex.setText(i+1, 0, ""+ result.get(i).getRbId());
				    		flex.setText(i+1, 1, ""+ result.get(i).getRaavareId());
				    		flex.setText(i+1, 2, ""+ result.get(i).getMaengde());

				    }
				    
				    flex.setStyleName("FlexTable");
					
				}

			});
			
	
		    vPanel.add(flex);
		   // vPanel.setStyleName("Content");
		
		
		}	
	}


