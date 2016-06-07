package cdio.shared;

import java.util.List;

import cdio.shared.RaavareDTO;

public interface RaavareDAO {
	RaavareDTO getRaavare(int raavareId) throws DALException;
	List<RaavareDTO> getRaavareList() throws DALException;
	void createRaavare(RaavareDTO raavare) throws DALException;
	void updateRaavare(RaavareDTO raavare) throws DALException;
}
