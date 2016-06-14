package cdio.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cdio.shared.ProduktBatchDTO;
import cdio.shared.ProduktBatchKompDTO;
import cdio.shared.RaavareBatchDTO;
import cdio.shared.RaavareDTO;
import cdio.shared.ReceptDTO;
import cdio.shared.ReceptKompDTO;
import cdio.shared.UserDTO;

@RemoteServiceRelativePath("cdioservice")
public interface Service extends RemoteService {

	public List<UserDTO> getPersons(String token);

	public String checkLogin(int id, String password);

	public List<RaavareDTO> getRaavare(String token);

	public String getRole(String token);

	public List<RaavareBatchDTO> getRaavareBatches();

	public List<ProduktBatchDTO> getPB(String token);

	void updateRB(String token, RaavareBatchDTO RaavareBatch);

	void updatePB(String token, ProduktBatchDTO rB);

	public List<ReceptDTO> getRecept(String token);

	void updateRecept(String token, ReceptDTO rB);

	void updateRaavare(String token, RaavareDTO raavare);

	void updateUser(String token, UserDTO user);

	void createUser(String token, UserDTO user);

	void createRB(String token, RaavareBatchDTO raavarebatch);

	void createPB(String token, ProduktBatchDTO produktbatch);

	void createRecept(String token, ReceptDTO recept);

	boolean checkRecept(String token, String receptNavn);

	List<String> getRaavIRec(int recId);

	void createReceptKomponent(String token, ReceptKompDTO receptKomp);

	int getRaavareFromName(String name);

	void deleteUser(String token, int id);

	public ProduktBatchDTO getSpecificPB(int id, String token);

	public List<ReceptKompDTO> getReceptKompsFromReceptID(int id, String token);

	public RaavareDTO getRaavareFromID(int id, String token);

	public String getUserName(String token);

	void createRA(String token, RaavareDTO raavare);




}
