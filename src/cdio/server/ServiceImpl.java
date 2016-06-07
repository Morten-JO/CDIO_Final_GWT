package cdio.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cdio.client.service.Service;
import dto01917.UserDTO;

public class ServiceImpl extends RemoteServiceServlet implements Service {

	@Override
	public List<UserDTO> getPersons() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
