package cdio.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;

import cdio.client.service.ServiceClientImpl;

public class Content extends Composite {
	private HorizontalPanel vPanel;


	public Content(ServiceClientImpl client) {
		vPanel = new HorizontalPanel();
		initWidget(vPanel);
		vPanel.setStyleName("Content");

	}

	public void clearPanel() {
		this.vPanel.clear();

	}

	public void addContent(Composite comp) {
		this.vPanel.clear();

		this.vPanel.add(comp);

	}
}
