package cdio.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import cdio.shared.RaavareDTO;
import cdio.shared.UserDTO;



public interface ServiceAsync {

	
	void getPersons(AsyncCallback<UserDTO[]> callback);
	void getRaavare(int id, AsyncCallback<RaavareDTO> callback);
	void number(String s, AsyncCallback<String> callback);
}
