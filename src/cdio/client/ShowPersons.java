package cdio.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio.client.service.ServiceClientImpl;
import cdio.shared.UserDTO;

public class ShowPersons extends Composite {

	private Button[] delete;
	private Button[] change;
	private Label lbl;

	private VerticalPanel vPanel;

	public ShowPersons(ServiceClientImpl client) {
		vPanel = new VerticalPanel();
		initWidget(vPanel);
		lbl = new Label("trollelelelelelelelelel");
		//lbl.setStyleName("Header-Text");
		this.vPanel.add(lbl);
		
		client.service.getPersons(new AsyncCallback<UserDTO[]>() {
			
			@Override
			public void onSuccess(UserDTO[] result) {
				ShowPersons.this.lbl.setText("nusnus");
				delete = new Button[result.length];
			    change = new Button[result.length];
			    for(int i = 0; i < result.length; i++){
			     Label label = new Label(result[i].getNavn()+" , "+result[i].getOprId()+" , "+result[i].getCpr());
			     vPanel.add(label);
			     delete[i] = new Button("Delete");
			     vPanel.add(delete[i]);
			     change[i] = new Button("Change");
			     vPanel.add(change[i]);
			     vPanel.add(label); 
			    
			    }
			   
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				ShowPersons.this.lbl.setText("nusnus");
				
			}
		});
}
	
	
	
	}