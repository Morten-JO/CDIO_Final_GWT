package cdio.server.DAOinterfaces;

import java.sql.ResultSet;
import java.util.List;

import cdio.shared.ReceptDTO;

public interface ReceptDAO {
	ReceptDTO getRecept(int receptId) throws DALException;
	List<ReceptDTO> getReceptList() throws DALException;
	void createRecept(ReceptDTO recept) throws DALException;
	void updateRecept(ReceptDTO recept) throws DALException;
	ResultSet getFullRecept(String navn);
}
