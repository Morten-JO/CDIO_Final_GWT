package cdio.server;

import java.sql.SQLException;
import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import cdio.client.service.Service;
import cdio.server.DAOinterfaces.DALException;
import cdio.shared.ProduktBatchDTO;
import cdio.shared.RaavareBatchDTO;
import cdio.shared.RaavareDTO;
import cdio.shared.ReceptDTO;
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
	public List<RaavareDTO> getRaavare(String token) {
		if( getRole(token).equalsIgnoreCase("farmaceut")
				|| getRole(token).equalsIgnoreCase("admin")){
		try {
			return this.controller.getRaavareDAO().getRaavareList();
		} catch (cdio.server.DAOinterfaces.DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return null;
		
	}

	@Override
	public List<UserDTO> getPersons(String token) {
		
		if( getRole(token).equalsIgnoreCase("farmaceut")
				|| getRole(token).equalsIgnoreCase("admin")){
		try {
			return this.controller.getOprDAO().getUserList(getRole(token));
		} catch (cdio.server.DAOinterfaces.DALException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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





	@Override
	public List<ProduktBatchDTO> getPB(String token)  {
		if( getRole(token).equalsIgnoreCase("vaerkfoerer") || getRole(token).equalsIgnoreCase("farmaceut")
				|| getRole(token).equalsIgnoreCase("admin")){
			try {
				return controller.getPBDAO().getProduktBatchList();
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}





	@Override
	public List<ReceptDTO> getRecept(String token) {
		if( getRole(token).equalsIgnoreCase("farmaceut")
				|| getRole(token).equalsIgnoreCase("admin")){
			try {
				return controller.getRecDAO().getReceptList();
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return null;
	}





	@Override
	public void updateRB(String token, RaavareBatchDTO RaavareBatch) {
		if( getRole(token).equalsIgnoreCase("vaerkfoerer") || getRole(token).equalsIgnoreCase("farmaceut")
				|| getRole(token).equalsIgnoreCase("admin")){
			try {
				controller.getRBDAO().updateRaavareBatch(RaavareBatch);
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
}





	@Override
	public void updatePB(String token, ProduktBatchDTO PB) {
		if( getRole(token).equalsIgnoreCase("vaerkfoerer") || getRole(token).equalsIgnoreCase("farmaceut")
				|| getRole(token).equalsIgnoreCase("admin")){
			try {
				controller.getPBDAO().updateProduktBatch(PB);
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
		
	}

	@Override
	public void updateRecept(String token, ReceptDTO rB) {
		if( getRole(token).equalsIgnoreCase("vaerkfoerer") || getRole(token).equalsIgnoreCase("farmaceut")
				|| getRole(token).equalsIgnoreCase("admin")){
			try {
				controller.getRecDAO().updateRecept(rB);;
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
		
	}


	@Override
	public void updateRaavare(String token, RaavareDTO raavare) {
		if( getRole(token).equalsIgnoreCase("vaerkfoerer") || getRole(token).equalsIgnoreCase("farmaceut")
				|| getRole(token).equalsIgnoreCase("admin")){
			try {
				controller.getRaavareDAO().updateRaavare(raavare);;
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
		
	}





	@Override
	public void updateUser(String token, UserDTO user) {
		if( getRole(token).equalsIgnoreCase("vaerkfoerer") || getRole(token).equalsIgnoreCase("farmaceut")
				|| getRole(token).equalsIgnoreCase("admin")){
			try {
				controller.getOprDAO().updateOperatoer(user);
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	}





	@Override
	public void createUser(String token, UserDTO user) {
		if( getRole(token).equalsIgnoreCase("vaerkfoerer") || getRole(token).equalsIgnoreCase("farmaceut")
				|| getRole(token).equalsIgnoreCase("admin")){
			try {
				controller.getOprDAO().createOperatoer(user);
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
		
	}





	@Override
	public void createRB(String token, RaavareBatchDTO raavarebatch) {
		if( getRole(token).equalsIgnoreCase("vaerkfoerer") || getRole(token).equalsIgnoreCase("farmaceut")
				|| getRole(token).equalsIgnoreCase("admin")){
			try {
				controller.getRBDAO().createRaavareBatch(raavarebatch);
			} catch (DALException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	}
	
	
	@Override
	public void createPB(String token, ProduktBatchDTO produktbatch) {
		if( getRole(token).equalsIgnoreCase("vaerkfoerer") || getRole(token).equalsIgnoreCase("farmaceut")
				|| getRole(token).equalsIgnoreCase("admin")){
		
				try {
					controller.getPBDAO().createProduktBatch(produktbatch);
				} catch (DALException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		
	}
	}
	
	@Override
	public void createRecept(String token, ReceptDTO recept) {
		if( getRole(token).equalsIgnoreCase("vaerkfoerer") || getRole(token).equalsIgnoreCase("farmaceut")
				|| getRole(token).equalsIgnoreCase("admin")){
		
				try {
					controller.getRecDAO().createRecept(recept);
				} catch (DALException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		
	}
	}
	}


