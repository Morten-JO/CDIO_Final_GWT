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

	public List<UserDTO> getPersons();
	public String checkLogin(int id, String password);
	public List<RaavareDTO> getRaavare(String token);
	public String getRole(String token);
	public List<RaavareBatchDTO> getRaavareBatches();
	public List<ProduktBatchDTO> getPB(String token) ;
	public List<ReceptDTO> getRecept(String token);
	public void updateRB(String token, RaavareBatchDTO RaavareBatch);

	
}
