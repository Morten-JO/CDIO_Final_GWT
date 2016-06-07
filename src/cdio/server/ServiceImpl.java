package cdio.server;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cdio.client.service.Service;
import dto01917.UserDTO;

public class ServiceImpl extends RemoteServiceServlet implements Service {
	
	private DataController controller;
	
	public ServiceImpl() {
		controller = new DataController();
	}

	@Override
	public List<UserDTO> getPersons() {
		
		ArrayList<UserDTO> list = new ArrayList<UserDTO>();
		
		this.controller.getOprDAO();
		return list;
	}

	
}
