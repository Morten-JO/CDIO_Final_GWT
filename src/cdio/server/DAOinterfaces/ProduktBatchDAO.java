package cdio.server.DAOinterfaces;

import java.sql.SQLException;
import java.util.List;

import cdio.server.DTO.ProduktBatchDTO;

public interface ProduktBatchDAO {
	ProduktBatchDTO getProduktBatch(int pbId) throws DALException;
	List<ProduktBatchDTO> getProduktBatchList() throws DALException;
	void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException, SQLException;
	void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException;
}