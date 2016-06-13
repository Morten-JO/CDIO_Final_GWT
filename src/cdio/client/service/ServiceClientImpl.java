package cdio.client.service;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

public class ServiceClientImpl {

	public ServiceAsync service;

	public ServiceClientImpl(String url) {

		this.service = GWT.create(Service.class);
		ServiceDefTarget endpoint = (ServiceDefTarget) this.service;
		endpoint.setServiceEntryPoint(url);

	}

}
