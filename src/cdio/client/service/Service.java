package cdio.client.service;
import java.sql.ResultSet;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import cdio.shared.DALException;
import cdio.shared.RaavareDTO;
import cdio.shared.UserDTO;



@RemoteServiceRelativePath("cdioservice")
public interface Service extends RemoteService {

List<UserDTO> getPersons();


public String checkLogin(int id, String password);

public RaavareDTO getRaavare(int id);

//public String number(String s);
	
}
