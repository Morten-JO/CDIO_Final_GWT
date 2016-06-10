package cdio.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cdio.shared.DALException;
import cdio.shared.ProduktBatchDTO;
import cdio.shared.RaavareBatchDTO;
import cdio.shared.RaavareDTO;
import cdio.shared.ReceptDTO;
import cdio.shared.UserDTO;



@RemoteServiceRelativePath("cdioservice")
public interface Service extends RemoteService {

	public List<UserDTO> getPersons(String token);
	public String checkLogin(int id, String password);
	public List<RaavareDTO> getRaavare(String token);
	public String getRole(String token);
	public List<RaavareBatchDTO> getRaavareBatches();
	public List<ProduktBatchDTO> getPB(String token) ;	
	void updateRB(String token, RaavareBatchDTO RaavareBatch);
	void updatePB(String token, ProduktBatchDTO rB);
	public List<ReceptDTO> getRecept(String token);
	void updateRecept(String token, ReceptDTO rB);
	void updateRaavare(String token, RaavareDTO raavare);
	void updateUser(String token, UserDTO user);
	void createUser(String token, UserDTO user);
	void createRB(String token,RaavareBatchDTO raavarebatch);
	


	
}
