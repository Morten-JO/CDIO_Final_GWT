package cdio.client.service;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cdio.shared.RaavareDTO;
import cdio.shared.UserDTO;



@RemoteServiceRelativePath("cdioservice")
public interface Service extends RemoteService {

public	UserDTO[] getPersons();

public RaavareDTO getRaavare(int id);

	
	
}
