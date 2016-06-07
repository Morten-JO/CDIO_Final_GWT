package daoimpl01917;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector01917.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.RaavareBatchDAO;
import dto01917.RaavareBatchDTO;

public class MYSQLRaavareBatchDAO implements RaavareBatchDAO{

	@Override
	public RaavareBatchDTO getRaavareBatch(int rbId) throws DALException {
		try {
	    	 CallableStatement getRB = (CallableStatement) Connector.getInstance().getConnection().prepareCall("call get_raavarebatch(?)");
			    getRB.setInt(1, rbId);
			    ResultSet rs = getRB.executeQuery();
			    if (rs.first()){			    	
			    	int raavare_id = rs.getInt(2);
			    	double maengde = rs.getDouble(3);
			    	RaavareBatchDTO newpb = new RaavareBatchDTO(rbId, raavare_id, maengde);
			    	return newpb;
			    }
	    }
	    catch (SQLException e) {throw new DALException(e); }
	    return null;
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList() throws DALException {
		List<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		try
		{
			ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM view_raavarebatch");
			while (rs.next()) 
			{
				RaavareBatchDTO current = new RaavareBatchDTO(rs.getInt(1), rs.getInt(2), rs.getDouble(3));
				list.add(current);
			}
		}
		catch (SQLException e) {
			throw new DALException(e); 
		}
		return list;
	}

	@Override
	public List<RaavareBatchDTO> getRaavareBatchList(int raavareId) throws DALException {
		List<RaavareBatchDTO> list = new ArrayList<RaavareBatchDTO>();
		try
		{
			ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM view_raavarebatch where raavare_id = "+raavareId);
			while (rs.next()) 
			{
				RaavareBatchDTO current = new RaavareBatchDTO(rs.getInt(1), rs.getInt(2), rs.getDouble(3));
				list.add(current);
			}
		}
		catch (SQLException e) { 
			throw new DALException(e); 
		}
		return list;
	}

	@Override
	public void createRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		try {
		    CallableStatement createOP = (CallableStatement) Connector.getInstance().getConnection().prepareCall("call add_raavarebatch(?,?,?)");
		    createOP.setInt(1, raavarebatch.getRaavareId());
		    createOP.setDouble(2, raavarebatch.getMaengde());
		    createOP.setInt(3, 0);
		    createOP.execute();
		    
		    if (createOP.getInt(3) == 1){
		    	int id = 0;
			    ResultSet rs = Connector.getInstance().doQuery("select max(rb_id) from view_raavarebatch;");
				if (rs.first()){   
					id =rs.getInt(1);		
				}
				raavarebatch.setRbId(id);
		    }
		    else {
		    	System.err.println("Return value of 'createRaavareBatch' was a error value");
		    }
		}
		catch (SQLException e) {
		    System.err.println("Cannot create raavarebatch, check weather or not the referenced Recept_id exists");
		}
	}

	@Override
	public void updateRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		try {
			Connector.getInstance().doUpdate(
					"UPDATE raavarebatch SET  raavare_id = " + raavarebatch.getRaavareId() + ", maengde =  " + raavarebatch.getMaengde() + 
					" WHERE rb_id = " + raavarebatch.getRbId()
			);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
