package cdio.server.DAOinterfaces;

import java.util.List;

import cdio.shared.RollerDTO;

public interface RollerDAO {

	RollerDTO getRolle(int oprId) throws DALException;
	List<RollerDTO> getRolleList() throws DALException;
	void createRolle(RollerDTO opr) throws DALException;
	void updateRolle(RollerDTO opr, String rolle) throws DALException;
	
}
