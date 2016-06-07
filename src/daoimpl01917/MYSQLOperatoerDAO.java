package daoimpl01917;

import java.sql.CallableStatement;
import java.sql.ResultSet;
//import 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector01917.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.OperatoerDAO;
import dto01917.OperatoerDTO;

public class MYSQLOperatoerDAO implements OperatoerDAO {
	
	public OperatoerDTO getOperatoer(int oprId) throws DALException {
		try {
			CallableStatement getOP = (CallableStatement) Connector.getInstance().getConnection().prepareCall("call get_operatoer(?)");
			getOP.setInt(1, oprId);
			ResultSet rs = getOP.executeQuery();
			if (rs.first()){			    	
				String opr_navn = rs.getString(2);
			    String opr_ini = rs.getString(3);
			    String opr_cpr = rs.getString(4);
			    String opr_password = rs.getString(5);
			    
			    OperatoerDTO newopr = new OperatoerDTO(opr_navn, opr_ini, opr_cpr, opr_password);
			    newopr.setOprId(oprId);
			    return newopr;
			}
	    } catch (SQLException e) {
	    	throw new DALException(e); 
	    }
	    return null;
	}
	
	public void createOperatoer(OperatoerDTO opr) throws DALException {  
		try {
			int id = 0;
			CallableStatement createOP = (CallableStatement) Connector.getInstance().getConnection().prepareCall("call add_operatoer(?,?,?,?)");
			createOP.setString(1, opr.getOprNavn());
			createOP.setString(2, opr.getIni());
			createOP.setString(3, opr.getCpr());
			createOP.setString(4, opr.getPassword());
			createOP.execute();   
			ResultSet rs = Connector.getInstance().doQuery("select max(opr_id) from operatoer;");
			if (rs.first()){
				id = rs.getInt(1);
				opr.setOprId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not create operatoer, check if the database is running!");
		}
	}
	
	public void updateOperatoer(OperatoerDTO opr, int id) throws DALException {
		try {
			CallableStatement updateOP = (CallableStatement) Connector.getInstance().getConnection().prepareCall("call update_operatoer(?,?,?,?,?)");
			updateOP.setString(1, opr.getOprNavn());
			updateOP.setString(2, opr.getIni());
			updateOP.setString(3, opr.getCpr());
			updateOP.setString(4, opr.getPassword());
			updateOP.setInt(5, id);
			updateOP.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<OperatoerDTO> getOperatoerList() throws DALException {
		List<OperatoerDTO> list = new ArrayList<OperatoerDTO>();
		try
		{
			ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM view_operatoer");
			while (rs.next()) 
			{
				OperatoerDTO current = new OperatoerDTO(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				current.setOprId(rs.getInt(1));
				list.add(current);
				 
			}
		} catch (SQLException e) {
			throw new DALException(e); 
		}
		return list;
	}
}