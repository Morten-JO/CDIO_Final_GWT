package cdio.server.DAOImpl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
//import 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cdio.server.Connector;
import cdio.server.DAOinterfaces.DALException;
import cdio.server.DAOinterfaces.OperatoerDAO;
import cdio.shared.UserDTO;

public class MYSQLOperatoerDAO implements OperatoerDAO {
	
	public UserDTO getOperatoer(int oprId) throws DALException {
		try {
			CallableStatement getOP = (CallableStatement) Connector.getInstance().getConnection().prepareCall("call get_operatoer(?)");
			getOP.setInt(1, oprId);
			ResultSet rs = getOP.executeQuery();
			if (rs.first()){			    	
				String oprNavn = rs.getString(2);
			    String ini = rs.getString(3);
			    String cpr = rs.getString(4);
			    String password = rs.getString(5);
			    String rolle = rs.getString(6);
			    
			   UserDTO newopr = new UserDTO(oprId, oprNavn, ini, cpr, password, rolle);
			    newopr.setOprId(oprId);
			    return newopr;
			}
	    } catch (SQLException e) {
	    	throw new DALException(e); 
	    }
	    return null;
	}
	
	public void createOperatoer(UserDTO opr) throws DALException {  
		try {
			CallableStatement createOP = (CallableStatement) Connector.getInstance().getConnection().prepareCall("call add_operatoer(?,?,?,?,?,?)");
			createOP.setInt(1, opr.getOprId());
			createOP.setString(2, opr.getNavn());
			createOP.setString(3, opr.getIni());
			createOP.setString(4, opr.getCpr());
			createOP.setString(5, opr.getPassword());
			createOP.setString(6, opr.getRolle());
			createOP.execute();
			
//			int id = 0;
//			ResultSet rs = Connector.getInstance().doQuery("select max(opr_id) from operatoer;");
//			if (rs.first()){
//				id = rs.getInt(1);
//				opr.setOprId(id);
//			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not create operatoer! The operator ID is proably taken!");
		}
	}
	
	public void updateOperatoer(UserDTO opr, int id) throws DALException {
		try {
			CallableStatement updateOP = (CallableStatement) Connector.getInstance().getConnection().prepareCall("call update_operatoer(?,?,?,?,?,?)");
			updateOP.setString(1, opr.getNavn());
			updateOP.setString(2, opr.getIni());
			updateOP.setString(3, opr.getCpr());
			updateOP.setString(4, opr.getPassword());
			updateOP.setString(5, opr.getRolle());
			updateOP.setInt(6, id);
			updateOP.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 */
	public List<UserDTO> getUserList(UserDTO opr) throws DALException {
		List<UserDTO> list = new ArrayList<UserDTO>();
		try
		{
			ResultSet rs;
			
			if(opr.getRolle().equals("admin")){
				rs = Connector.getInstance().doQuery("SELECT * FROM operatoer");
			}
			else{
				rs = Connector.getInstance().doQuery("SELECT * FROM view_operatoer");
			}
			while (rs.next()) 
			{
				UserDTO current = new UserDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				current.setOprId(rs.getInt(1));
				list.add(current);
				 
			}
		} catch (SQLException e) {
			throw new DALException(e); 
		}
		return list;
	}

	@Override
	public String getUserRole(String token) throws DALException {
		String role = null;
		try {
			System.out.println(token);
			ResultSet rs =  Connector.getInstance().doQuery("SELECT rolle FROM operatoer WHERE oprId = " + token+";");
			System.out.println("jensen");
			rs.next();
			role = rs.getString(1);
			System.out.println("rolle" + role);
			return role;
		} catch (Exception e) {
			System.out.println("datafail");
		}
		System.out.println("her er vi");
		return role;
	}


	
	






	
}