package cdio.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import cdio.client.service.*;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CDIO_Final implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	@Override
	public void onModuleLoad() {
		final ServiceClientImpl client = new ServiceClientImpl(GWT.getModuleBaseURL() + "cdioservice");
		Login page = new Login(client);
		RootPanel.get().add(page);
	}
}
