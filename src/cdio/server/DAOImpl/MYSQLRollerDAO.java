package cdio.server.DAOImpl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cdio.server.Connector;
import cdio.server.DAOinterfaces.DALException;
import cdio.server.DAOinterfaces.RollerDAO;
import cdio.shared.RollerDTO;
public class MYSQLRollerDAO implements RollerDAO{

	@Override
	public RollerDTO getRolle(int oprId) throws DALException {
		try {
			CallableStatement getOP = (CallableStatement) Connector.getInstance().getConnection().prepareCall("call get_rolle(?)");
			getOP.setInt(1, oprId);
			ResultSet rs = getOP.executeQuery();
			if (rs.first()){		
				String rolle = rs.getString(1);
			    RollerDTO newrolle = new RollerDTO(oprId, rolle);
			    return newrolle;
			}
	    } catch (SQLException e) {
	    	throw new DALException(e); 
	    }
	    return null;
	}

	@Override
	public List<RollerDTO> getRolleList() throws DALException {
		List<RollerDTO> list = new ArrayList<RollerDTO>();
		try
		{
			ResultSet rs = Connector.getInstance().doQuery("SELECT * FROM view_roller");
			while (rs.next()) 
			{
				RollerDTO current = new RollerDTO(rs.getInt(1), rs.getString(3));
				list.add(current);
				 
			}
		} catch (SQLException e) {
			throw new DALException(e); 
		}
		return list;
	}

	@Override
	public void createRolle(RollerDTO rolle) throws DALException {
		try {
			CallableStatement createRolle = (CallableStatement) Connector.getInstance().getConnection().prepareCall("call update_roller(?,?)");
			createRolle.setInt(1, rolle.getOpr_id());
			createRolle.setString(2, rolle.getRolle());
			createRolle.execute();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Could not create roller, check if the database is running!");
		}
	}

	@Override
	public void updateRolle(RollerDTO opr, String rolle) {
		try {
			CallableStatement updateRolle = (CallableStatement) Connector.getInstance().getConnection().prepareCall("call update_roller(?,?)");
			updateRolle.setInt(1, opr.getOpr_id());
			updateRolle.setString(2, rolle);
			updateRolle.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
