package cdio.server;

import java.util.List;

import org.eclipse.jdt.internal.compiler.lookup.ReductionResult;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cdio.client.service.Service;
import cdio.server.DAOinterfaces.DALException;
import cdio.shared.RaavareDTO;
import cdio.shared.UserDTO;

public class ServiceImpl extends RemoteServiceServlet implements Service {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2491989177309231572L;
	private DataController controller;
	private TokenHandler token;
	
	public ServiceImpl() {
		controller = new DataController();
		token = new TokenHandler();
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
					token = this.token.createToken(Integer.toString(id));
			return token;
		}else{
			return "Login not authorized!";
		}
	}
}

