package cdio.client.service;
import daointerfaces01917.*;
import dto01917.UserDTO;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


@RemoteServiceRelativePath("cdioservice")
public interface Service extends RemoteService {

	List<UserDTO> getPersons();

	
	
}
