package cdio.client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio.client.service.ServiceClientImpl;
import cdio.shared.UserDTO;

public class ShowPersons extends Composite {

	private Button[] delete;
	private Button[] change;
	
	private FlexTable flex;

	private VerticalPanel vPanel;

	public ShowPersons(ServiceClientImpl client) {
		flex = new FlexTable();
		vPanel = new VerticalPanel();
		initWidget(vPanel);
		
		//lbl.setStyleName("Header-Text");
		//this.vPanel.add(lbl);
		
		client.service.getPersons(new AsyncCallback<List<UserDTO>>() {
			
		
	
			@Override
			public void onFailure(Throwable caught) {
				//ShowPersons.this.lbl.setText("jensen");
				
			}

			@Override
			public void onSuccess(List<UserDTO> result) {
			//	ShowPersons.this.lbl.setText("nusnus");
				delete = new Button[result.size()];
				change = new Button[result.size()];
				
				flex.setText(0, 0, "Id");
				flex.setText(0, 1, "Navn");
				flex.setText(0, 2, "Ini");
				flex.setText(0, 3, "Cpr");
				flex.setText(0, 4, "Password");
				flex.setText(0, 5, "Rolle");
			    for(int i = 0; i < result.size(); i++){
			    	
			    		flex.setText(i+1, 0, "" + result.get(i).getOprId());
			    		flex.setText(i+1, 1, result.get(i).getNavn());
			    		flex.setText(i+1, 2, result.get(i).getIni());
			    		flex.setText(i+1, 3, result.get(i).getCpr());
			    		flex.setText(i+1, 4, result.get(i).getPassword());
			    		flex.setText(i+1, 5, result.get(i).getRolle());
			    }
			    
			    flex.setStyleName("FlexTable");
				
			}

		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	    vPanel.add(flex);
	   // vPanel.setStyleName("Content");
	
	
	}	
	}