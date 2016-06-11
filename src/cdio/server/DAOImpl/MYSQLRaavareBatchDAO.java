package cdio.server.DAOImpl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cdio.server.Connector;
import cdio.server.DAOinterfaces.DALException;
import cdio.server.DAOinterfaces.RaavareBatchDAO;
import cdio.shared.RaavareBatchDTO;

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
			ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM raavarebatch");
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
			ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM view_raavarebatch where raavareId = "+raavareId);
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
			Connector.getInstance().doQuery(
					"SELECT add_raavarebatch('"+raavarebatch.getRbId()+"','"+
												raavarebatch.getRaavareId()+"','"+
												raavarebatch.getMaengde()+"');");
			
		}
		catch (SQLException e) {
		    System.err.println("Cannot create raavarebatch, check weather or not the referenced raavareId exists"+e);
		}
	}

	@Override
	public void updateRaavareBatch(RaavareBatchDTO raavarebatch) throws DALException {
		try {
			Connector.getInstance().doUpdate( 
					"SELECT update_raavarebatch('"+	raavarebatch.getRbId()+ "','"+
													raavarebatch.getRaavareId() + "','"+
													raavarebatch.getMaengde() + "');");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
