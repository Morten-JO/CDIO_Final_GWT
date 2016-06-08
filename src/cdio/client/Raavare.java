package cdio.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import cdio.client.service.ServiceClientImpl;
import cdio.shared.RaavareDTO;

public class Raavare extends Composite{

	private VerticalPanel vPanel;
	private Label lbl;
	
	
	public Raavare(ServiceClientImpl client){
		vPanel = new VerticalPanel();
		initWidget(vPanel);
		
		client.service.getRaavare(1, new AsyncCallback<RaavareDTO>() {
			
			@Override
			public void onSuccess(RaavareDTO result) {
				lbl = new Label(""+ result.getRaavareNavn());
				vPanel.add(lbl);
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				lbl = new Label("Everything failed");
				vPanel.add(lbl);
				
				
			}
		});
		
//		client.service.number("jensen", new AsyncCallback<String>() {
//			
//			@Override
//			public void onSuccess(String result) {
//				lbl = new Label(result);
//				vPanel.add(lbl);
//				
//			}
//			
//			@Override
//			public void onFailure(Throwable caught) {
//				lbl = new Label("tosse");
//				vPanel.add(lbl);
//				
//			}
//		});
		
	}
	
	public void setlbl (String s){
		lbl = new Label(s);
	}
	
}
