package daoimpl01917;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector01917.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.RaavareDAO;
import dto01917.RaavareDTO;

public class MYSQLRaavareDAO implements RaavareDAO{
	
	@Override
	public RaavareDTO getRaavare(int raavareId) throws DALException {
		try {
			CallableStatement getraavare = (CallableStatement) Connector.getInstance().getConnection().prepareCall("call get_raavare(?)");
			getraavare.setInt(1, raavareId);
			ResultSet rs = getraavare.executeQuery();
			if (rs.first()){			    	
				String raavare_navn = rs.getString(2);
				String leverandoer = rs.getString(3);
				RaavareDTO newRec = new RaavareDTO(raavareId, raavare_navn, leverandoer);
				return newRec;
			}
		}
		catch (SQLException e) {
			throw new DALException(e); 
		}
		return null;
	}

	@Override
	public List<RaavareDTO> getRaavareList() throws DALException {
		List<RaavareDTO> list = new ArrayList<RaavareDTO>();
		try
		{
			ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM view_raavare");
			while (rs.next()) 
			{
				RaavareDTO current = new RaavareDTO(rs.getInt(1), rs.getString(2), rs.getString(3));
				list.add(current);
			}
		}
		catch (SQLException e) {
			throw new DALException(e); 
		}
		return list;
	}

	@Override
	public void createRaavare(RaavareDTO raavare) throws DALException {
		try {
			int id = 0;
		    CallableStatement createRaavare = (CallableStatement) Connector.getInstance().getConnection().prepareCall("call add_raavare(?,?)");
		    createRaavare.setString(1, raavare.getRaavareNavn());
		    createRaavare.setString(2, raavare.getLeverandoer());
		    createRaavare.execute();
		    ResultSet rs = Connector.getInstance().doQuery("select max(raavare_id) from view_raavare;");
		    if (rs.first()){   
		    	id =rs.getInt(1);		
		    }
			raavare.setRaavareId(id);
		   } catch (Exception e) {
			   e.printStackTrace();
			   System.err.println("Could not create Raavare, check if the database is running!");
		   }
	}

	@Override
	public void updateRaavare(RaavareDTO raavare) throws DALException {
		try {
			Connector.getInstance().doUpdate(
					"UPDATE raavare SET  raavare_navn= '" + raavare.getRaavareNavn() + "', leverandoer = '"
							+ raavare.getLeverandoer() + "' WHERE raavare_id = " + raavare.getRaavareId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}