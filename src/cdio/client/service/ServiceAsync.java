package cdio.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;


import cdio.shared.ProduktBatchDTO;
import cdio.shared.RaavareBatchDTO;
import cdio.shared.RaavareDTO;
import cdio.shared.ReceptDTO;
import cdio.shared.UserDTO;



public interface ServiceAsync {

	
	void getPersons(String token, AsyncCallback<List<UserDTO>> callback);
	void getRaavare(String token, AsyncCallback<List<RaavareDTO>> callback);
	void checkLogin(int id, String password, AsyncCallback<String> callback);
	void getRole(String token, AsyncCallback<String> callback);
	void getRaavareBatches(AsyncCallback<List<RaavareBatchDTO>> asyncCallback);
	void getPB(String token, AsyncCallback<List<ProduktBatchDTO>> callback);
	void getRecept(String token, AsyncCallback<List<ReceptDTO>> callback);
	void updateRB(String token, RaavareBatchDTO RaavareBatch, AsyncCallback<Void> callback);
	void updatePB(String token, ProduktBatchDTO rB, AsyncCallback<Void> asyncCallback);
	void updateRecept(String token, ReceptDTO rB, AsyncCallback<Void> asyncCallback);
	void updateRaavare(String token, RaavareDTO raavare, AsyncCallback<Void> asyncCallback);
	void updateUser(String token, UserDTO user, AsyncCallback<Void> asyncCallback);



}
