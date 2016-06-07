package cdio.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cdio.client.Raavare;
import cdio.client.service.Service;
import cdio.client.service.ServiceClientImpl;
import cdio.shared.RaavareDTO;
import cdio.shared.UserDTO;
import cdio.shared.DALException;

public class ServiceImpl extends RemoteServiceServlet implements Service {
	
	private DataController controller;
	
	public ServiceImpl() {
		controller = new DataController();
	}

//	@Override
//	public UserDTO[] getPersons() {
//
//		
//		try {
//			return this.controller.getOprDAO().getOperatoerList();
//		} catch (DALException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//		
//		
//
//	
//}

	
	
	@Override
	public RaavareDTO getRaavare(int id) {
		
		try {
			System.out.println("JEnsen er den bedste");
			return this.controller.getRaavareDAO().getRaavare(1);
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new RaavareDTO(100, "jensen", "Jensen");
		
	}

	@Override
	public UserDTO[] getPersons() {
		// TODO Auto-generated method stub
		return null;
	}
}

