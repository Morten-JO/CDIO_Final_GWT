package cdio.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cdio.client.service.Service;
import cdio.server.DAOinterfaces.DALException;
import cdio.shared.RaavareBatchDTO;
import cdio.shared.RaavareDTO;
import cdio.shared.UserDTO;

public class ServiceImpl extends RemoteServiceServlet implements Service {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2491989177309231572L;
	private DataController controller;
	private TokenHandler tokenHandler;
	
	public ServiceImpl() {
		controller = new DataController();
		tokenHandler = new TokenHandler();
	}



	
	
	@Override
	public RaavareDTO getRaavare(int id) {
		
		try {
			return this.controller.getRaavareDAO().getRaavare(1);
		} catch (cdio.server.DAOinterfaces.DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new RaavareDTO(100, "jensen", "Jensen");
		
	}

	@Override
	public List<UserDTO> getPersons() {
		try {
			return this.controller.getOprDAO().getUserList();
		} catch (cdio.server.DAOinterfaces.DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String checkLogin(int id, String password) {
		String token = null;
		UserDTO current = null;
		try {
			current = this.controller.getOprDAO().getOperatoer(id);
		} catch (DALException e) {
			e.printStackTrace();
		}
		//System.out.println(current);
		if (current != null && current.getPassword().equals(password)){
				if(current.getRolle().equals("admin")||current.getRolle().equals("vaerkfoerer")||current.getRolle().equals("farmaceut"))
					token = tokenHandler.createToken(Integer.toString(id));
			return token;
		}else{
			return "Login not authorized!";
		}
	}

	@Override
	public String getRole(String token) {
		
		try {
			String r = tokenHandler.getUserID(token);
			String role = controller.getOprDAO().getUserRole(tokenHandler.getUserID(token));
			System.out.println("hulubulu---------"+role);
			return role;
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return null;
	}





	@Override
	public List<RaavareBatchDTO> getRaavareBatches() {
		try {
			return controller.getRBDAO().getRaavareBatchList();
		} catch (DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

