package daointerfaces01917;

import java.sql.SQLException;
import java.util.List;

import dto01917.ProduktBatchDTO;

public interface ProduktBatchDAO {
	ProduktBatchDTO getProduktBatch(int pbId) throws DALException;
	List<ProduktBatchDTO> getProduktBatchList() throws DALException;
	void createProduktBatch(ProduktBatchDTO produktbatch) throws DALException, SQLException;
	void updateProduktBatch(ProduktBatchDTO produktbatch) throws DALException;
}